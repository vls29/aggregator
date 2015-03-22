package uk.co.vsf.aggregator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PvOutputCalendarUtils extends CalendarUtils {
    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public static String getDate(Calendar calendar) {
        return DATE_FORMAT.format(calendar.getTime());
    }
}
