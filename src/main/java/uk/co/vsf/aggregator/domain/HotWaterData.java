package uk.co.vsf.aggregator.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Map;

public class HotWaterData extends DefaultReadingData {

	private BigDecimal hotWaterTemperature;
	private boolean immersionOn = false;

	public HotWaterData(Calendar received, Map<String, String> data) {
		super(received);
		hotWaterTemperature = new BigDecimal((String) data.get("t"));
		hotWaterTemperature.setScale(2, RoundingMode.HALF_EVEN);
		
		Integer immersionStatus = Integer.parseInt((String) data.get("i"));
		if (immersionStatus > 0) {
			this.immersionOn = true;
		}
	}

	public BigDecimal getHotWaterTemperature() {
		return hotWaterTemperature;
	}

	public boolean getImmersionOn() {
		return immersionOn;
	}
}
