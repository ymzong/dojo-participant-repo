/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 *
 * THIS SOFTWARE CONTAINS PROPRIETARY AND CONFIDENTIAL INFORMATION OWNED BY PALANTIR TECHNOLOGIES INC.
 * UNAUTHORIZED DISCLOSURE TO ANY THIRD PARTY IS STRICTLY PROHIBITED
 *
 * For good and valuable consideration, the receipt and adequacy of which is acknowledged by Palantir and recipient
 * of this file ("Recipient"), the parties agree as follows:
 *
 * This file is being provided subject to the non-disclosure terms by and between Palantir and the Recipient.
 *
 * Palantir solely shall own and hereby retains all rights, title and interest in and to this software (including,
 * without limitation, all patent, copyright, trademark, trade secret and other intellectual property rights) and
 * all copies, modifications and derivative works thereof.  Recipient shall and hereby does irrevocably transfer and
 * assign to Palantir all right, title and interest it may have in the foregoing to Palantir and Palantir hereby
 * accepts such transfer. In using this software, Recipient acknowledges that no ownership rights are being conveyed
 * to Recipient.  This software shall only be used in conjunction with properly licensed Palantir products or
 * services.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING
 * IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import can.touch.Customer;
import can.touch.CustomerRepository;
import can.touch.TotalOrderValue;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.logging.LogDirectory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RealCustomerRepositoryShould {
    private static final Customer AARON = new Customer(2, "Aaron", "contact@email.test");
    private static final Customer BOB = new Customer(15, "Bob", "bob@email.test");
    private static final Customer CAROL = new Customer(20, "Carol", "carol@email.test");

    @Rule
    public DockerComposeRule docker = DockerComposeRule.builder()
            .file("docker-compose.yml")
            .saveLogsTo(LogDirectory.circleAwareLogDirectory(RealCustomerRepositoryShould.class))
            .build();

    private CustomerRepository repo;

    @Before
    public void setup() {
        repo = CustomerRepository.createDefaultOnPort(postgresPort());

        repo.createCustomerTable();
        repo.createOrderTable();
    }

    @Test public void
    load_customers_after_storing_them() {
        repo.insertCustomer(AARON.getId(), AARON.getName(), AARON.getContact());

        Customer customer = repo.getCustomer(AARON.getId());

        assertThat(customer).isEqualTo(AARON);

        repo.close();
    }

    @Test public void
    return_one_customer_total_order_value() {
        repo.insertCustomer(AARON.getId(), AARON.getName(), AARON.getContact());
        repo.insertOrder(500, AARON.getId());
        List<TotalOrderValue> result = repo.getTotalOrderValues();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(AARON.getId());
        assertThat(result.get(0).getOrderTotal()).isEqualTo(500);
        repo.close();
    }

    @Test public void
    return_one_customer_total_order_value_two_orders() {
        repo.insertCustomer(AARON.getId(), AARON.getName(), AARON.getContact());
        repo.insertOrder(500, AARON.getId());
        repo.insertOrder(1000, AARON.getId());
        List<TotalOrderValue> result = repo.getTotalOrderValues();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getCustomerId()).isEqualTo(AARON.getId());
        assertThat(result.get(0).getOrderTotal()).isEqualTo(1500);
        repo.close();
    }

    @Test public void
    return_two_customer_total_order_values_two_orders_each() {
        repo.insertCustomer(AARON.getId(), AARON.getName(), AARON.getContact());
        repo.insertCustomer(BOB.getId(), BOB.getName(), BOB.getContact());
        repo.insertOrder(500, AARON.getId());
        repo.insertOrder(1000, AARON.getId());
        repo.insertOrder(600, BOB.getId());
        repo.insertOrder(2000, BOB.getId());
        List<TotalOrderValue> result = repo.getTotalOrderValues();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getCustomerId()).isEqualTo(AARON.getId());
        assertThat(result.get(0).getOrderTotal()).isEqualTo(1500);
        assertThat(result.get(1).getCustomerId()).isEqualTo(BOB.getId());
        assertThat(result.get(1).getOrderTotal()).isEqualTo(2600);
        repo.close();
    }

    private int postgresPort() {
        return docker.containers().container("db").port(5432).getExternalPort();
    }
}
