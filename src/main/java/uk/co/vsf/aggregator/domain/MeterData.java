package uk.co.vsf.aggregator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MeterData extends DefaultReadingData {

    private static final BigDecimal MILLISECONDS_IN_HOURS = new BigDecimal("3600000");

    private BigDecimal importImpulses;
    private BigDecimal importMultiplier;
    private BigDecimal exportImpulses;
    private BigDecimal exportMultiplier;
    private BigDecimal msBetweenCalls;
    private BigDecimal mainsVoltage;

    private BigDecimal importWatts;

    public MeterData(Calendar received, Map<String, String> data) {
        super(received);

        if (!(StringUtils.isBlank(data.get("importImpulses")))) {
            this.importImpulses = new BigDecimal(data.get("importImpulses").trim());

            if (!(StringUtils.isBlank(data.get("importMultiplier")))) {
                this.importMultiplier = new BigDecimal(data.get("importMultiplier").trim());
                this.importWatts = this.importImpulses.multiply(this.importMultiplier);
            } else {
                this.importWatts = this.importImpulses.multiply(new BigDecimal("1.25"));
            }
        }

        if (StringUtils.isBlank(data.get("msBetweenCalls"))) {
            this.msBetweenCalls = new BigDecimal("60000");
        } else {
            this.msBetweenCalls = new BigDecimal(data.get("msBetweenCalls").trim());
        }

        if (!StringUtils.isBlank(data.get("mainsVoltage"))) {
            this.mainsVoltage = new BigDecimal(data.get("mainsVoltage").trim());
        }
    }

    public BigDecimal getImportWatts() {
        if (importWatts == null) {
            return null;
        }
        return importWatts.divide(getWattHoursDivisor(), 0, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getWattHoursDivisor() {
        return msBetweenCalls.divide(MILLISECONDS_IN_HOURS, 5, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getExportWatts() {
        if (exportImpulses == null) {
            return null;
        } else if (exportImpulses.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return exportImpulses.divide(getWattHoursDivisor(), 0, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getImportImpulses() {
        return importImpulses;
    }

    public BigDecimal getExportImpulses() {
        return exportImpulses;
    }

    public BigDecimal getMsBetweenCalls() {
        return msBetweenCalls;
    }

    public BigDecimal getImportMultiplier() {
        return importMultiplier;
    }

    public BigDecimal getExportMultiplier() {
        return exportMultiplier;
    }

    public BigDecimal getMainsVoltage() {
        return mainsVoltage;
    }
}
