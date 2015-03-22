package uk.co.vsf.aggregator.domain;

import java.util.Calendar;

public interface ReadingData {

	public Calendar getReceived();
	
	/**
	 * Gets the received Date time as YYYY-MM-DD
	 * @return
	 */
	public String getReceivedDateTime();
}
