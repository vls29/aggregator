package uk.co.vsf.aggregator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

public class PvOutputUploadTest extends AbstractDatabaseTest {

    @Override
    protected String getConfigResources() {
        return "pvoutput-upload.xml, generic.xml, aCtx-aggregator.xml, pvoutput-endpoint-mock.xml";
    }

    @Test
    public void getDataToUpload() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("EXPORTWATTS", new BigDecimal("18.96300"));
        data.put("DATABASE_DATE", "2015-01-08");
        data.put("DATE", "20150108");
        data.put("ID", "123");
        data.put("UPLOADED", false);
        data.put("GUSTSPEED", new BigDecimal("14.4300"));
        data.put("ENERGYGENWATTHRS", new BigInteger("10"));
        data.put("HOTWATER", new BigDecimal("40.560"));
        data.put("DATABASE_TIME", "19:54:00");
        data.put("TIME", "19:54");
        data.put("IMMERSION", true);
        data.put("POWERGENWATTS", new BigInteger("17"));
        data.put("IMPORTWATTS", new BigDecimal("12.569"));
        data.put("WINDSPEED", new BigDecimal("0.198"));

        List<Object> wrapper = new ArrayList<Object>();
        wrapper.add(data);

        MuleClient client = new MuleClient(muleContext);
        Map<String, Object> properties = new HashMap<String, Object>();
        MuleMessage message = new DefaultMuleMessage(wrapper, properties, muleContext);
        client.send("vm://pvoutput-upload", message);
        Thread.sleep(4000);
    }

}
