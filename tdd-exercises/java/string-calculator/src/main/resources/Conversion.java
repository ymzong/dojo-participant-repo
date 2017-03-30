/**
 * Created by jzong on 3/30/17.
 */
public class Conversion {
    static String ones[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",  "X"};
    static String tens[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
    static String hundreds[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "DM", "M"};

    public static String parse(int i) {
        String h = hundreds[i/100];
        String t = tens[(i%100)/10];
        return h + t + ones[i%10];
    }
}
