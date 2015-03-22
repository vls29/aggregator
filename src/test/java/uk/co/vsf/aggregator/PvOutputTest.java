package uk.co.vsf.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import uk.co.vsf.aggregator.pvoutput.mapper.AddStatusRequestMapper;

public class PvOutputTest extends AbstractDatabaseTest {

    private AddStatusRequestMapper addStatusRequestMapper;
    @Override
    protected String getConfigResources() {
        return "store.xml, pvoutput-data-selection.xml, generic.xml, aCtx-aggregator.xml";
    }

    @Before
    public void before() {
        super.before();
        addStatusRequestMapper = muleContext.getRegistry().get("AddStatusRequestMapper");
    }

    @Test
    public void getDataToUpload() throws Exception {
        Calendar cal = new GregorianCalendar();

        StoreTest storeTest = new StoreTest();
        storeTest.generationData(cal);
        storeTest.hotWaterData(cal);
        storeTest.meterData(cal);
        storeTest.weatherData(cal);

        Thread.sleep(3000);

        assertEquals(1, rowCount(TABLE_GENERATION));
        assertEquals(1, rowCount(TABLE_HOT_WATER));
        assertEquals(1, rowCount(TABLE_METER));
        assertEquals(1, rowCount(TABLE_WEATHER));
        assertEquals(1, rowCount(TABLE_UPLOADED));

        cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, -5);

        storeTest.generationData(cal);
        storeTest.hotWaterData(cal);
        storeTest.meterData(cal);
        storeTest.weatherData(cal);

        Thread.sleep(3000);

        assertEquals(2, rowCount(TABLE_GENERATION));
        assertEquals(2, rowCount(TABLE_HOT_WATER));
        assertEquals(2, rowCount(TABLE_METER));
        assertEquals(2, rowCount(TABLE_WEATHER));
        assertEquals(2, rowCount(TABLE_UPLOADED));

        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage("", properties, muleContext);
        client.send("vm://pvoutput", message);

        MuleMessage output = client.request("vm://pvoutput-upload", 10000);
        assertNotNull(output);
        assertTrue(output.getPayload() instanceof List);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getDataToUploadPushThroughMapperOk() throws Exception {
        Calendar cal = new GregorianCalendar();

        StoreTest storeTest = new StoreTest();
        storeTest.generationData(cal);
        storeTest.hotWaterData(cal);
        storeTest.meterData(cal);
        storeTest.weatherData(cal);

        Thread.sleep(3000);

        assertEquals(1, rowCount(TABLE_GENERATION));
        assertEquals(1, rowCount(TABLE_HOT_WATER));
        assertEquals(1, rowCount(TABLE_METER));
        assertEquals(1, rowCount(TABLE_WEATHER));
        assertEquals(1, rowCount(TABLE_UPLOADED));

        cal = new GregorianCalendar();
        cal.add(Calendar.MINUTE, -5);

        storeTest.generationData(cal);
        storeTest.hotWaterData(cal);
        storeTest.meterData(cal);
        storeTest.weatherData(cal);

        Thread.sleep(3000);

        assertEquals(2, rowCount(TABLE_GENERATION));
        assertEquals(2, rowCount(TABLE_HOT_WATER));
        assertEquals(2, rowCount(TABLE_METER));
        assertEquals(2, rowCount(TABLE_WEATHER));
        assertEquals(2, rowCount(TABLE_UPLOADED));

        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage("", properties, muleContext);
        client.send("vm://pvoutput", message);

        MuleMessage output = client.request("vm://pvoutput-upload", 10000);
        assertNotNull(output);
        assertTrue(output.getPayload() instanceof List);
        
        Map mappedValues = addStatusRequestMapper.mapValues((Map<String, Object>)((List<Map<String, Object>>)output.getPayload()).get(0));
        assertNotNull(mappedValues.get("v8"));
    }
}
