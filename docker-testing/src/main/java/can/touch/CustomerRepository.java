package can.touch;/*
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

import cannot.touch.DataSourceFactory;
import cannot.touch.Retrying;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface CustomerRepository {
    static CustomerRepository createDefaultOnPort(int port) {
        DBI dbi = new DBI(DataSourceFactory.create(port));
        try {
            return Retrying.withRetry( () -> dbi.open(CustomerRepository.class));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @SqlUpdate("create table customers (id int primary key, name varchar(100), contact varchar(100))")
    void createCustomerTable();

    @SqlUpdate("create table orders (order_value int, customer_id int)")
    void createOrderTable();

    @SqlUpdate("insert into customers (id, name, contact) values (:id, :name, :contact)")
    void insertCustomer(@Bind("id") int id, @Bind("name") String name, @Bind("contact") String contact);

    @SqlUpdate("insert into orders (order_value, customer_id) values (:order_value, :customer_id)")
    void insertOrder(@Bind("order_value") int orderValue, @Bind("customer_id") int customerId);

    /*
    ------ THIS IS AN EXAMPLE -------
    | This is an example of how     |
    | to create an object from a    |
    | SQL query                     |
    ---------------------------------
    */
    @SqlQuery("select * from customers where id = :id")
    @Mapper(CustomerMapper.class)
    Customer getCustomer(@Bind("id") int id);

    /*
    ---- ADD IMPLEMENTATION HERE ----
    | This method should get the    |
    | value of orders for each      |
    | customer                      |
    ---------------------------------
    */
    @SqlQuery("select customer_id, sum(order_value) as value from orders group by customer_id order by customer_id")
    @Mapper(TotalOrderValueMapper.class)
    List<TotalOrderValue> getTotalOrderValues();
    /**
     * close with no args is used to close the connection
     */
    void close();
}
