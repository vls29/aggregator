package uk.co.vsf.aggregator.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Map;

public class WeatherData extends DefaultReadingData {

    private BigDecimal circumference;
    private BigInteger windCount;
    private BigInteger gustCount;

    /**
     * Meters per second to miles per hour.
     */
    private BigDecimal mpsToMph = new BigDecimal("2.2369");

    public WeatherData(Calendar received, Map<String, String> data) {
        super(received);
        this.circumference = new BigDecimal(data.get("circ"));
        this.windCount = new BigInteger(data.get("windOn").trim());
        this.gustCount = new BigInteger(data.get("gustMax").trim());
    }

    /**
     * Calculates the wind speed by using the circumference of the wind
     * anemometer.
     * 
     * @return wind speed.
     */
    public BigDecimal calculateWindSpeed() {
        if (windCount.equals(BigInteger.ZERO)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = new BigDecimal(windCount);
        // divide by number of seconds in a minute
        value = value.divide(new BigDecimal("60"), new MathContext(6, RoundingMode.HALF_EVEN));
        value = value.multiply(circumference).multiply(mpsToMph);
        value = value.setScale(2, RoundingMode.HALF_EVEN);
        return value;
    }

    /**
     * Calculates the gust speed by using the circumference of the wind
     * anemometer.
     * 
     * @return gust speed.
     */
    public BigDecimal calculateGustSpeed() {
        if (gustCount.equals(BigInteger.ZERO)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = new BigDecimal(gustCount);
        value = value.multiply(circumference).multiply(mpsToMph);
        value = value.setScale(2, RoundingMode.HALF_EVEN);
        return value;
    }

    public BigDecimal getCircumference() {
        return circumference;
    }

    public BigInteger getWindCount() {
        return windCount;
    }

    public BigInteger getGustCount() {
        return gustCount;
    }
}
