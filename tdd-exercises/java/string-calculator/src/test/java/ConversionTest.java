import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by jzong on 3/30/17.
 */
public class ConversionTest {
    @Test
    public void shouldReturnIIfOne() {
        assertThat(Conversion.parse(1)).isEqualTo("I");
    }
}
