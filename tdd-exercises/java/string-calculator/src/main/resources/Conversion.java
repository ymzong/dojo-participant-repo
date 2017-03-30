/**
 * Created by jzong on 3/30/17.
 */
public class Conversion {
    static String digit[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",  "X"};
    static String tens[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};

    public static String parse(int i) {

        String result = tens[i / 10];
        return result + digit[i%10];
    }
}
