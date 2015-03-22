package uk.co.vsf.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.transport.http.HttpResponse;

import uk.co.vsf.aggregator.domain.HotWaterData;

public class HotWaterDisplayTest extends AbstractDatabaseTest {

    @Override
    protected String getConfigResources() {
        return "store.xml, hot-water-display.xml, generic.xml, aCtx-aggregator.xml";
    }

    @Test
    public void getDisplayData_Level() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_HOT_WATER));

        MuleClient client = new MuleClient(muleContext);

        BigDecimal temperature = new BigDecimal("40.56");
        BigInteger immersion = new BigInteger("10");

        Map<String, String> data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData1 = new HotWaterData(getCalendarPlusXMinutes(0), data);
        HotWaterData hotWaterData2 = new HotWaterData(getCalendarPlusXMinutes(2), data);
        HotWaterData hotWaterData3 = new HotWaterData(getCalendarPlusXMinutes(4), data);
        HotWaterData hotWaterData4 = new HotWaterData(getCalendarPlusXMinutes(6), data);
        HotWaterData hotWaterData5 = new HotWaterData(getCalendarPlusXMinutes(8), data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message1 = new DefaultMuleMessage(hotWaterData1, properties, muleContext);
        MuleMessage message2 = new DefaultMuleMessage(hotWaterData2, properties, muleContext);
        MuleMessage message3 = new DefaultMuleMessage(hotWaterData3, properties, muleContext);
        MuleMessage message4 = new DefaultMuleMessage(hotWaterData4, properties, muleContext);
        MuleMessage message5 = new DefaultMuleMessage(hotWaterData5, properties, muleContext);

        client.send("vm://store", message1);
        client.send("vm://store", message2);
        client.send("vm://store", message3);
        client.send("vm://store", message4);
        client.send("vm://store", message5);
        
        Thread.sleep(5000);
        MuleMessage outputMessage = new DefaultMuleMessage("", properties, muleContext);
        
        MuleMessage output = client.send("vm://hot-water-display", outputMessage);
        String response = ((HttpResponse)output.getPayload()).getBodyAsString();
        System.out.println(response);
        assertTrue(response.contains("LEVEL"));
    }

    @Test
    public void getDisplayData_Increasing() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_HOT_WATER));

        MuleClient client = new MuleClient(muleContext);

        BigDecimal temperature = new BigDecimal("40.56");
        BigInteger immersion = new BigInteger("10");

        Map<String, String> data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData1 = new HotWaterData(getCalendarPlusXMinutes(0), data);
        HotWaterData hotWaterData2 = new HotWaterData(getCalendarPlusXMinutes(2), data);
        HotWaterData hotWaterData3 = new HotWaterData(getCalendarPlusXMinutes(4), data);
        
        temperature = new BigDecimal("40.57");
        immersion = new BigInteger("10");
        
        data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData4 = new HotWaterData(getCalendarPlusXMinutes(6), data);
        
        temperature = new BigDecimal("40.58");
        immersion = new BigInteger("10");
        
        data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData5 = new HotWaterData(getCalendarPlusXMinutes(8), data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message1 = new DefaultMuleMessage(hotWaterData1, properties, muleContext);
        MuleMessage message2 = new DefaultMuleMessage(hotWaterData2, properties, muleContext);
        MuleMessage message3 = new DefaultMuleMessage(hotWaterData3, properties, muleContext);
        MuleMessage message4 = new DefaultMuleMessage(hotWaterData4, properties, muleContext);
        MuleMessage message5 = new DefaultMuleMessage(hotWaterData5, properties, muleContext);

        client.send("vm://store", message1);
        client.send("vm://store", message2);
        client.send("vm://store", message3);
        client.send("vm://store", message4);
        client.send("vm://store", message5);
        
        Thread.sleep(5000);
        MuleMessage outputMessage = new DefaultMuleMessage("", properties, muleContext);
        
        MuleMessage output = client.send("vm://hot-water-display", outputMessage);
        String response = ((HttpResponse)output.getPayload()).getBodyAsString();
        System.out.println(response);
        assertTrue(response.contains("INCREASING"));
    }

    @Test
    public void getDisplayData_Decreasing() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_HOT_WATER));

        MuleClient client = new MuleClient(muleContext);

        BigDecimal temperature = new BigDecimal("40.56");
        BigInteger immersion = new BigInteger("10");

        Map<String, String> data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData1 = new HotWaterData(getCalendarPlusXMinutes(0), data);
        HotWaterData hotWaterData2 = new HotWaterData(getCalendarPlusXMinutes(2), data);
        HotWaterData hotWaterData3 = new HotWaterData(getCalendarPlusXMinutes(4), data);
        
        temperature = new BigDecimal("40.55");
        immersion = new BigInteger("10");
        
        data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData4 = new HotWaterData(getCalendarPlusXMinutes(6), data);
        
        temperature = new BigDecimal("40.54");
        immersion = new BigInteger("10");
        
        data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData5 = new HotWaterData(getCalendarPlusXMinutes(8), data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message1 = new DefaultMuleMessage(hotWaterData1, properties, muleContext);
        MuleMessage message2 = new DefaultMuleMessage(hotWaterData2, properties, muleContext);
        MuleMessage message3 = new DefaultMuleMessage(hotWaterData3, properties, muleContext);
        MuleMessage message4 = new DefaultMuleMessage(hotWaterData4, properties, muleContext);
        MuleMessage message5 = new DefaultMuleMessage(hotWaterData5, properties, muleContext);

        client.send("vm://store", message1);
        client.send("vm://store", message2);
        client.send("vm://store", message3);
        client.send("vm://store", message4);
        client.send("vm://store", message5);
        
        Thread.sleep(5000);
        MuleMessage outputMessage = new DefaultMuleMessage("", properties, muleContext);
        
        MuleMessage output = client.send("vm://hot-water-display", outputMessage);
        String response = ((HttpResponse)output.getPayload()).getBodyAsString();
        System.out.println(response);
        assertTrue(response.contains("DECREASING"));
    }

    @Test
    public void getDisplayData_NoReadsingsSince() throws MuleException, Exception {
        assertEquals(0, rowCount(TABLE_HOT_WATER));

        MuleClient client = new MuleClient(muleContext);

        BigDecimal temperature = new BigDecimal("40.56");
        BigInteger immersion = new BigInteger("10");

        Map<String, String> data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData1 = new HotWaterData(new GregorianCalendar(2015, 0, 03, 1, 02), data);
        HotWaterData hotWaterData2 = new HotWaterData(new GregorianCalendar(2015, 0, 03, 1, 03), data);
        HotWaterData hotWaterData3 = new HotWaterData(new GregorianCalendar(2015, 0, 03, 1, 04), data);
        
        temperature = new BigDecimal("40.55");
        immersion = new BigInteger("10");
        
        data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData4 = new HotWaterData(new GregorianCalendar(2015, 0, 03, 1, 05), data);
        
        temperature = new BigDecimal("40.54");
        immersion = new BigInteger("10");
        
        data = new HashMap<String, String>();
        data.put("t", temperature.toPlainString());
        data.put("i", immersion.toString());
        HotWaterData hotWaterData5 = new HotWaterData(new GregorianCalendar(2015, 0, 03, 1, 06), data);

        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message1 = new DefaultMuleMessage(hotWaterData1, properties, muleContext);
        MuleMessage message2 = new DefaultMuleMessage(hotWaterData2, properties, muleContext);
        MuleMessage message3 = new DefaultMuleMessage(hotWaterData3, properties, muleContext);
        MuleMessage message4 = new DefaultMuleMessage(hotWaterData4, properties, muleContext);
        MuleMessage message5 = new DefaultMuleMessage(hotWaterData5, properties, muleContext);

        client.send("vm://store", message1);
        client.send("vm://store", message2);
        client.send("vm://store", message3);
        client.send("vm://store", message4);
        client.send("vm://store", message5);
        
        Thread.sleep(5000);
        MuleMessage outputMessage = new DefaultMuleMessage("", properties, muleContext);
        
        MuleMessage output = client.send("vm://hot-water-display", outputMessage);
        String response = ((HttpResponse)output.getPayload()).getBodyAsString();
        System.out.println(response);
        assertTrue(response.contains("No readings seen since "));
    }
    
    private Calendar getCalendarPlusXMinutes(int xMinutes)
    {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, xMinutes);
        return cal;
    }
}
