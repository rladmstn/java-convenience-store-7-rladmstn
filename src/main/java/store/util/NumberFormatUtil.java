package store.util;

import java.text.DecimalFormat;

public final class NumberFormatUtil {
    private static final String COMMA_FORMAT = "###,###";

    public static String numberFormat(int number){
        DecimalFormat decimalFormat = new DecimalFormat(COMMA_FORMAT);
        return decimalFormat.format(number);
    }
}
