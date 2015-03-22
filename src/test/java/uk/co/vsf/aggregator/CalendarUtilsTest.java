package uk.co.vsf.aggregator;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;

import org.junit.Test;

import uk.co.vsf.aggregator.CalendarUtils;

public class CalendarUtilsTest {

    @Test
    public void getDate() {
        assertEquals("1999-12-31", CalendarUtils.getDate(new GregorianCalendar(1999, 11, 31)));
        assertEquals("2000-01-25", CalendarUtils.getDate(new GregorianCalendar(2000, 0, 25)));
        assertEquals("2101-01-01", CalendarUtils.getDate(new GregorianCalendar(2101, 0, 1)));
        assertEquals("2015-02-03", CalendarUtils.getDate(new GregorianCalendar(2015, 01, 03)));
    }

    @Test
    public void getTime() {
        assertEquals("00:00:00", CalendarUtils.getTime(new GregorianCalendar(1999, 11, 31, 0, 0)));
        assertEquals("01:15:00", CalendarUtils.getTime(new GregorianCalendar(2000, 0, 25, 1, 15)));
        assertEquals("14:18:00", CalendarUtils.getTime(new GregorianCalendar(2101, 0, 1, 14, 18)));
        assertEquals("23:59:00", CalendarUtils.getTime(new GregorianCalendar(2015, 01, 03, 23, 59)));
    }

    @Test
    public void getHour() {
        assertEquals("00", CalendarUtils.getHour(new GregorianCalendar(1999, 11, 31, 0, 0)));
        assertEquals("01", CalendarUtils.getHour(new GregorianCalendar(2000, 0, 25, 1, 15)));
        assertEquals("14", CalendarUtils.getHour(new GregorianCalendar(2101, 0, 1, 14, 18)));
        assertEquals("23", CalendarUtils.getHour(new GregorianCalendar(2015, 01, 03, 23, 59)));
    }

    @Test
    public void getMinute() {
        assertEquals("00", CalendarUtils.getMinute(new GregorianCalendar(1999, 11, 31, 0, 0)));
        assertEquals("15", CalendarUtils.getMinute(new GregorianCalendar(2000, 0, 25, 1, 15)));
        assertEquals("18", CalendarUtils.getMinute(new GregorianCalendar(2101, 0, 1, 14, 18)));
        assertEquals("59", CalendarUtils.getMinute(new GregorianCalendar(2015, 01, 03, 23, 59)));
    }
}
