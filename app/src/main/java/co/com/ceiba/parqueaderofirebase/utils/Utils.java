package co.com.ceiba.parqueaderofirebase.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    public static String getDateHourInFormat(long timeInMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy HH:mm");
        return sdf.format(timeInMillis);
    }
}
