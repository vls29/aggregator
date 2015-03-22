package uk.co.vsf.aggregator.pvoutput.mapper;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AddStatusRequestMapperTest {
    
    private String date = "20150117";
    private String time = "11:15";

    @Test
    public void mapData() throws Exception {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("DATE", date);
        data.put("TIME", time);

        AddStatusRequestMapper requestMapper = new AddStatusRequestMapper();
        InputStream inputStream = getClass().getResourceAsStream("/AddStatusMapping.xml");
        requestMapper.setMappingFileData(inputStream);
        requestMapper.init();

        Map<String, String> output = requestMapper.mapValues(data);
        
        assertEquals(3, output.size());
        
        assertEquals(date, output.get("d"));
        assertEquals(time, output.get("t"));
        assertEquals("1", output.get("c1"));
    }
}
