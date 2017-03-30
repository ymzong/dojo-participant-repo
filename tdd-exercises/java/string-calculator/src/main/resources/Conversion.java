/**
 * Created by jzong on 3/30/17.
 */
public class Conversion {
    static String digit[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",  "X"};

    public static String parse(int i) {

        String result = "";
        for (int j = 0; j < i / 10; j++) {
            result += "X";
        }
        if (i > 40) result = "XL";
        return result + digit[i%10];
    }
}
