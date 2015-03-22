package uk.co.vsf.aggregator.component;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import uk.co.vsf.aggregator.component.HotWaterDisplayComponent.HotWaterStatus;

public class HotWaterDisplayComponentTest {

    private class HotWaterDisplayComponentExtended extends HotWaterDisplayComponent {
        @Override
        public HotWaterStatus getHotWaterStatus(BigDecimal hotWater1, BigDecimal hotWater2, BigDecimal hotWater3, BigDecimal hotWater4,
                BigDecimal hotWater5) {
            return super.getHotWaterStatus(hotWater1, hotWater2, hotWater3, hotWater4, hotWater5);
        }
    }

    @Test
    public void status() {
        HotWaterDisplayComponentExtended extended = new HotWaterDisplayComponentExtended();
        assertEquals(HotWaterStatus.INCREASING, extended.getHotWaterStatus(new BigDecimal("10"), new BigDecimal("9.9"), new BigDecimal(
                "9.8"), BigDecimal.ZERO, BigDecimal.ZERO));

        assertEquals(HotWaterStatus.INCREASING, extended.getHotWaterStatus(new BigDecimal("10.01"), new BigDecimal("10.00"),
                new BigDecimal("9.99"), BigDecimal.ZERO, BigDecimal.ZERO));

        assertEquals(HotWaterStatus.DECREASING, extended.getHotWaterStatus(new BigDecimal("10"), new BigDecimal("10.01"),
                new BigDecimal("10.02"), BigDecimal.ZERO, BigDecimal.ZERO));

        assertEquals(HotWaterStatus.DECREASING, extended.getHotWaterStatus(new BigDecimal("10.01"), new BigDecimal("10.02"),
                new BigDecimal("10.03"), BigDecimal.ZERO, BigDecimal.ZERO));

        assertEquals(HotWaterStatus.LEVEL, extended.getHotWaterStatus(new BigDecimal("10.01"), new BigDecimal("10.02"),
                new BigDecimal("10.01"), BigDecimal.ZERO, BigDecimal.ZERO));

        assertEquals(HotWaterStatus.LEVEL, extended.getHotWaterStatus(new BigDecimal("10.01"), new BigDecimal("10.01"),
                new BigDecimal("10.01"), BigDecimal.ZERO, BigDecimal.ZERO));
    }
}
