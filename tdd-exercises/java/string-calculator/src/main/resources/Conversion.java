/**
 * Created by jzong on 3/30/17.
 */
public class Conversion {
    static String ones[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX",  "X"};
    static String tens[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};
    static String hundreds[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM", "M"};
    static String thousands[] = {"", "M", "MM", "MMM", "MMMM"};

    public static String parse(int i) {
        String th = thousands[i/1000];
        String h = hundreds[(i/100)%10];
        String t = tens[(i/10)%10];
        return th + h + t + ones[i%10];
    }
}
