package uk.co.vsf.aggregator.domain;

import java.util.Calendar;

/**
 * All reading data extends DefaultReadingData and contains a received calendar
 * object which represents the date and time at which the data request was
 * received/taken.
 */
public class DefaultReadingData implements ReadingData {

    private Calendar received;

    protected DefaultReadingData(Calendar received) {
        this.received = received;
    }

    public Calendar getReceived() {
        return received;
    }

    public String getReceivedDateTime() {
        // TODO Auto-generated method stub
        return null;
    }
}