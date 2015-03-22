package uk.co.vsf.aggregator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    private static DateFormat HOUR_FORMAT = new SimpleDateFormat("HH");
    private static DateFormat MINUTE_FORMAT = new SimpleDateFormat("mm");

    public static String getDate(Calendar calendar) {
        return DATE_FORMAT.format(calendar.getTime());
    }

    public static String getTime(Calendar calendar) {
        return TIME_FORMAT.format(calendar.getTime()) + ":00";
    }

    public static String getHour(Calendar calendar) {
        return HOUR_FORMAT.format(calendar.getTime());
    }

    public static String getMinute(Calendar calendar) {
        return MINUTE_FORMAT.format(calendar.getTime());
    }
}
