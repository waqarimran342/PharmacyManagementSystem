package Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseDate(String s) throws Exception {
        return sdf.parse(s);
    }

    public static String formatDate(Date d) {
        return sdf.format(d);
    }
}
