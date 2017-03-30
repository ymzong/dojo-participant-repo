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

    @Test
    public void shouldReturnXXIfTwenty() {
        assertThat(Conversion.parse(20)).isEqualTo("XX");
    }

    @Test
    public void shouldReturnXXXIXIf39() {
        assertThat(Conversion.parse(39)).isEqualTo("XXXIX");
    }

    @Test
    public void shoudlReturnXLIXIf49() {
        assertThat(Conversion.parse(49)).isEqualTo("XLIX");
    }

    @Test
    public void should_Return_LXXVI_if_76() {
        assertThat(Conversion.parse(76)).isEqualTo("LXXVI");
    }

    @Test
    public void should_Return_C_if_100() {
        assertThat(Conversion.parse(100)).isEqualTo("C");
    }

    @Test
    public void should_return_XCIX_if_99() {
        assertThat(Conversion.parse(99)).isEqualTo("XCIX");
    }

    @Test
    public void should_return_DI_if_501() {
        assertThat(Conversion.parse(501)).isEqualTo("DI");
    }
}
