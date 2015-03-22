package uk.co.vsf.aggregator.component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import uk.co.vsf.aggregator.DateTimeUtils;

public class HotWaterDisplayComponent {

    // Time, Temperature, Status, I, Immersion Status, Temperature Description
    private static final String DISPLAY = "%s %sC %s I %s, %s";

    private int maxTimeSinceLastReadingMinutes;

    public enum HotWaterStatus {
        INCREASING("U"), LEVEL("L"), DECREASING("D");

        private String display;

        private HotWaterStatus(String display) {
            this.display = display;
        }

        public String getDisplay() {
            return this.display;
        }
    }

    private enum TemperatureDescription {
        FREEZING(new BigDecimal("35")), COLD(new BigDecimal("40")), MILD(new BigDecimal("45")), WARM(new BigDecimal("50")), HOT(
                new BigDecimal("55")), BOILING(new BigDecimal("55")), WARNING(new BigDecimal("100")), ;

        private BigDecimal upperLimit;

        private TemperatureDescription(BigDecimal upperLimit) {
            this.upperLimit = upperLimit;
        }

        public static String getTemperatureDescriptionFor(BigDecimal temperature) {
            if (temperature.compareTo(FREEZING.upperLimit) < 0) {
                return FREEZING.name();
            } else if (temperature.compareTo(COLD.upperLimit) < 0) {
                return COLD.name();
            } else if (temperature.compareTo(MILD.upperLimit) < 0) {
                return MILD.name();
            } else if (temperature.compareTo(WARM.upperLimit) < 0) {
                return WARM.name();
            } else if (temperature.compareTo(HOT.upperLimit) < 0) {
                return HOT.name();
            } else if (temperature.compareTo(BOILING.upperLimit) < 0) {
                return BOILING.name();
            }

            return WARNING.name();
        }
    }

    public String getHotWaterDisplay(List<Map<String, Object>> hotWaterData) {
        BigDecimal hotWater1 = (BigDecimal) hotWaterData.get(0).get("HOTWATER");
        boolean immersionStatus = (Boolean) hotWaterData.get(0).get("IMMERSION");
        BigDecimal hotWater2 = (BigDecimal) hotWaterData.get(1).get("HOTWATER");
        BigDecimal hotWater3 = (BigDecimal) hotWaterData.get(2).get("HOTWATER");
        BigDecimal hotWater4 = (BigDecimal) hotWaterData.get(3).get("HOTWATER");
        BigDecimal hotWater5 = (BigDecimal) hotWaterData.get(4).get("HOTWATER");

        HotWaterStatus status = getHotWaterStatus(hotWater1, hotWater2, hotWater3, hotWater4, hotWater5);
        String date = (String) hotWaterData.get(0).get("DATE");
        String time = (String) hotWaterData.get(0).get("TIME");
        String dateTime = DateTimeUtils.formatDateTime(date, time);

        Calendar lastReading = DateTimeUtils.getDateTimeAsCalendar(date, time);
        lastReading.add(Calendar.MINUTE, maxTimeSinceLastReadingMinutes);

        String output = null;
        if (Calendar.getInstance().compareTo(lastReading) <= 0) {
            output = formatOutputForDisplay(time.substring(0, 5), hotWater1 , immersionStatus, status);
        } else {
            output = "No readings seen since " + dateTime;
        }

        return output;
    }

    private String formatOutputForDisplay(String time, BigDecimal hotWaterTemperature, boolean immersionStatus,
            HotWaterStatus hotWaterStatus) {
        return String.format(DISPLAY, time, hotWaterTemperature.toPlainString(), hotWaterStatus, (immersionStatus ? "ON" : "OFF"),
                TemperatureDescription.getTemperatureDescriptionFor(hotWaterTemperature));
    }

    protected HotWaterStatus getHotWaterStatus(BigDecimal hotWater1, BigDecimal hotWater2, BigDecimal hotWater3, BigDecimal hotWater4,
            BigDecimal hotWater5) {
        if (hotWater1.compareTo(hotWater2) > 0) {
            if (hotWater2.compareTo(hotWater3) > 0) {
                return HotWaterStatus.INCREASING;
            }
        } else if (hotWater1.compareTo(hotWater2) < 0) {
            if (hotWater2.compareTo(hotWater3) < 0) {
                return HotWaterStatus.DECREASING;
            }
        }

        return HotWaterStatus.LEVEL;
    }

    public void setMaxTimeSinceLastReadingMinutes(int maxTimeSinceLastReadingMinutes) {
        this.maxTimeSinceLastReadingMinutes = maxTimeSinceLastReadingMinutes;
    }
}
