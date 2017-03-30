/**
 * Created by jzong on 3/30/17.
 */
public class Conversion {
    static String digit[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",  "X"};

    public static String parse(int i) {
        if (i >= 30) {
            return "XXX" + digit[i%10];
        }
        else if (i >= 20) {
            return "XX" + digit[i%10];
        }
        else if (i >= 10) {
            return "X" + digit[i%10];
        }
        return digit[i];
    }
}
