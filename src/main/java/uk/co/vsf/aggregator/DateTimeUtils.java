package uk.co.vsf.aggregator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateTimeUtils {

    public static String formatDateTime(String date, Integer hour, Integer minutes) {
        String time = formatIntegerToTimeComponent(hour) + ":" + formatIntegerToTimeComponent(minutes);
        return date + " " + time;
    }
    public static String formatDateTime(String date, String time) {
        return date + " " + time.substring(0, 5);
    }

    public static Calendar getDateTimeAsCalendar(String date, Integer hour, Integer minutes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = formatDateTime(date, hour, minutes);
        Calendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(dateTime));
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse date: " + dateTime);
        }
        return cal;
    }

    public static Calendar getDateTimeAsCalendar(String date, String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateTime = formatDateTime(date, time);
        Calendar cal = new GregorianCalendar();
        try {
            cal.setTime(sdf.parse(dateTime));
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse date: " + dateTime);
        }
        return cal;
    }

    private static String formatIntegerToTimeComponent(int timeComponent) {
        return timeComponent > 9 ? "" + timeComponent : "0" + timeComponent;
    }
}
