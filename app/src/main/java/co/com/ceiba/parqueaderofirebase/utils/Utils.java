package co.com.ceiba.parqueaderofirebase.utils;

import java.text.SimpleDateFormat;

public class Utils {

    private Utils() {
    }

    public static String getDateHourInFormat(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        return sdf.format(timeInMillis);
    }
}
