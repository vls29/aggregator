package uk.co.vsf.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import uk.co.vsf.aggregator.domain.DataSource;
import uk.co.vsf.aggregator.domain.GenerationData;
import uk.co.vsf.aggregator.domain.HotWaterData;
import uk.co.vsf.aggregator.domain.MeterData;
import uk.co.vsf.aggregator.domain.WeatherData;

public class InputTest extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "input.xml, generic.xml";
    }

    @Test
    public void hotWaterInput() throws MuleException {
        MuleClient client = new MuleClient(muleContext);

        BigDecimal temperature = new BigDecimal("40.56");
        BigInteger immersion = new BigInteger("10");

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("X-Data-Source", DataSource.HOT_WATER.name());
        properties.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String input = "t=" + temperature.toPlainString() + "&i=" + immersion.toString();

        properties.put("request.parameters", "{t=" + temperature.toPlainString()+", i=" + immersion.toString()+"}");
        properties.put("http.method", "POST");
        MuleMessage message = new DefaultMuleMessage(input, properties, muleContext);

        client.send("vm://pvoutput-data", message);
        MuleMessage output = client.request("vm://store", 5000);

        assertNull(output.getExceptionPayload());

        HotWaterData data = (HotWaterData) output.getPayload();

        assertNotNull(data.getReceived());
        assertEquals(temperature, data.getHotWaterTemperature());
        assertTrue(data.getImmersionOn());
    }

    @Test
    public void weatherInput() throws MuleException {
        MuleClient client = new MuleClient(muleContext);

        BigDecimal circumference = new BigDecimal("0.4056");
        BigInteger switchedOnCount = new BigInteger("10");
        BigInteger gustCount = new BigInteger("17");

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("X-Data-Source", DataSource.WEATHER.name());
        properties.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String input = "circ=" + circumference.toPlainString() + "&windOn=" + switchedOnCount.toString() + "&gustMax="
                + gustCount.toString();

        properties.put("request.parameters", input);
        MuleMessage message = new DefaultMuleMessage(input, properties, muleContext);

        client.send("vm://pvoutput-data", message);
        MuleMessage output = client.request("vm://store", 5000);

        assertNull(output.getExceptionPayload());

        WeatherData data = (WeatherData) output.getPayload();

        assertNotNull(data.getReceived());
        assertEquals(circumference, data.getCircumference());
        assertEquals(switchedOnCount, data.getWindCount());
        assertEquals(gustCount, data.getGustCount());
    }

    @Test
    public void inverterInput() throws MuleException {
        MuleClient client = new MuleClient(muleContext);

        BigInteger generation = new BigInteger("10");
        BigInteger power = new BigInteger("17");

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("X-Data-Source", DataSource.INVERTER.name());
        properties.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String input = "v1=" + generation + "&v2=" + power;

        properties.put("request.parameters", input);
        MuleMessage message = new DefaultMuleMessage(input, properties, muleContext);

        client.send("vm://pvoutput-data", message);
        MuleMessage output = client.request("vm://store", 5000);

        assertNull(output.getExceptionPayload());

        GenerationData data = (GenerationData) output.getPayload();

        assertNotNull(data.getReceived());
        assertEquals(generation, data.getEnergyGenerationWattHours());
        assertEquals(power, data.getPowerGenerationWatts());
    }

    @Test
    public void meterInput() throws MuleException {
        MuleClient client = new MuleClient(muleContext);

        BigInteger importImpulses = new BigInteger("10");
        BigDecimal importMultiplier = new BigDecimal("1.0");
        BigInteger exportImpulses = new BigInteger("17");
        BigDecimal exportMultiplier = new BigDecimal("1.25");
        BigDecimal msBetweenCalls = new BigDecimal("600");

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("X-Data-Source", DataSource.METER.name());
        properties.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        String input = "importImpulses=" + importImpulses + "&generationImpulses=" + exportImpulses + "&msBetweenCalls="
                + msBetweenCalls.toPlainString() + "&importMultiplier=" + importMultiplier.toPlainString() + "&generationMultiplier="
                + exportMultiplier.toPlainString();

        properties.put("request.parameters", input);
        MuleMessage message = new DefaultMuleMessage(input, properties, muleContext);

        client.send("vm://pvoutput-data", message);
        MuleMessage output = client.request("vm://store", 5000);

        assertNull(output.getExceptionPayload());

        MeterData data = (MeterData) output.getPayload();

        assertNotNull(data.getReceived());
        assertEquals(importImpulses, data.getImportImpulses().toBigInteger());
        assertEquals(importMultiplier, data.getImportMultiplier());
        assertNull(data.getExportImpulses());
        assertNull(data.getExportMultiplier());
        assertEquals(msBetweenCalls, data.getMsBetweenCalls());
    }
}
