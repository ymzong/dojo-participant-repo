/**
 * Created by jzong on 3/30/17.
 */
public class Conversion {
    static String digit[] = {"I", "II", "III", "IV"};

    public static String parse(int i) {
        return digit[i-1];
    }
}
