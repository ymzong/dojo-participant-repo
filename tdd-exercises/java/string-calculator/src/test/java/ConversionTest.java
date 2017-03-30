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

    @Test
    public void shouldReturnIIIfTwo() {
        assertThat(Conversion.parse(2)).isEqualTo("II");
    }

    @Test
    public void shouldReturnIVIfFour() {
        assertThat(Conversion.parse(4)).isEqualTo("IV");
    }

    @Test
    public void shouldReturnXIIfEleven() {
        assertThat(Conversion.parse(11)).isEqualTo("XI");
    }
}
