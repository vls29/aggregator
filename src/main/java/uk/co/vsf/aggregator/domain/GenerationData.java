package uk.co.vsf.aggregator.domain;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Map;

public class GenerationData extends DefaultReadingData {

    private BigInteger energyGenerationWattsHours;
    private BigInteger powerGenerationWatts;

    public GenerationData(Calendar received, Map<String, String> data) {
        super(received);
        energyGenerationWattsHours = new BigInteger(data.get("v1").trim());
        powerGenerationWatts = new BigInteger(data.get("v2").trim());
    }

    public BigInteger getEnergyGenerationWattHours() {
        return energyGenerationWattsHours;
    }

    public BigInteger getPowerGenerationWatts() {
        return powerGenerationWatts;
    }
}
