package uk.co.vsf.aggregator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import uk.co.vsf.aggregator.domain.GenerationData;
import uk.co.vsf.aggregator.domain.HotWaterData;
import uk.co.vsf.aggregator.domain.MeterData;
import uk.co.vsf.aggregator.domain.WeatherData;

public class StoreTest extends AbstractDatabaseTest {

    @Override
    protected String getConfigResources() {
        return "store.xml, generic.xml, aCtx-aggregator.xml";
    }

    @Test
    public void storeHotWaterData() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_HOT_WATER));
        assertEquals(0, rowCount(TABLE_UPLOADED));

        hotWaterData();
        Thread.sleep(3000);

        assertEquals(1, rowCount(TABLE_HOT_WATER));
        assertEquals(1, rowCount(TABLE_UPLOADED));
    }

    public void hotWaterData() throws Exception {
        hotWaterData(new GregorianCalendar());
    }

    public void hotWaterData(Calendar calendarToUse) throws Exception {
        MuleClient client = new MuleClient(muleContext);

        BigDecimal temperature = new BigDecimal("40.56");
        BigInteger immersion = new BigInteger("10");

        Map<String, String> data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData = new HotWaterData(calendarToUse, data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage(hotWaterData, properties, muleContext);

        client.send("vm://store", message);
    }

    @Test
    public void storeMeterData() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_METER));
        assertEquals(0, rowCount(TABLE_UPLOADED));

        meterData();
        Thread.sleep(3000);

        assertEquals(1, rowCount(TABLE_METER));
        assertEquals(1, rowCount(TABLE_UPLOADED));
    }

    public void meterData() throws Exception {
        meterData(new GregorianCalendar());
    }

    public void meterData(Calendar calendarToUse) throws Exception {
        MuleClient client = new MuleClient(muleContext);

        BigInteger importImpulses = new BigInteger("10");
        BigDecimal importMultiplier = new BigDecimal("1.0");
        BigInteger exportImpulses = new BigInteger("17");
        BigDecimal exportMultiplier = new BigDecimal("1.25");
        BigDecimal msBetweenCalls = new BigDecimal("600");

        Map<String, String> data = new HashMap<String, String>();
        data.put("importImpulses", importImpulses.toString());
        data.put("generationImpulses", exportImpulses.toString());
        data.put("msBetweenCalls", msBetweenCalls.toPlainString());
        data.put("importMultiplier", importMultiplier.toPlainString());
        data.put("generationMultiplier", exportMultiplier.toPlainString());
        MeterData meterData = new MeterData(calendarToUse, data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage(meterData, properties, muleContext);

        client.send("vm://store", message);
    }

    @Test
    public void storeWeatherData() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_WEATHER));
        assertEquals(0, rowCount(TABLE_UPLOADED));

        weatherData();
        Thread.sleep(3000);

        assertEquals(1, rowCount(TABLE_WEATHER));
        assertEquals(1, rowCount(TABLE_UPLOADED));
    }

    public void weatherData() throws Exception {
        weatherData(new GregorianCalendar());
    }

    public void weatherData(Calendar calendarToUse) throws Exception {
        MuleClient client = new MuleClient(muleContext);

        BigDecimal circumference = new BigDecimal("0.4056");
        BigInteger switchedOnCount = new BigInteger("10");
        BigInteger gustCount = new BigInteger("17");

        Map<String, String> data = new HashMap<String, String>();
        data.put("circ", circumference.toPlainString());
        data.put("windOn", switchedOnCount.toString());
        data.put("gustMax", gustCount.toString());
        WeatherData weatherData = new WeatherData(calendarToUse, data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage(weatherData, properties, muleContext);

        client.send("vm://store", message);
    }

    @Test
    public void storeGenerationData() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_GENERATION));
        assertEquals(0, rowCount(TABLE_UPLOADED));

        generationData();
        Thread.sleep(3000);

        assertEquals(1, rowCount(TABLE_GENERATION));
        assertEquals(1, rowCount(TABLE_UPLOADED));
    }

    public void generationData() throws Exception {
        generationData(new GregorianCalendar());
    }

    public void generationData(Calendar calendarToUse) throws Exception {
        MuleClient client = new MuleClient(muleContext);

        BigInteger generation = new BigInteger("10");
        BigInteger power = new BigInteger("17");

        Map<String, String> data = new HashMap<String, String>();
        data.put("v1", generation.toString());
        data.put("v2", power.toString());
        GenerationData generationData = new GenerationData(calendarToUse, data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage(generationData, properties, muleContext);

        client.send("vm://store", message);
    }
}
