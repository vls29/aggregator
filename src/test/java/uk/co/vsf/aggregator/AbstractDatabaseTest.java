package uk.co.vsf.aggregator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.mule.tck.junit4.FunctionalTestCase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractDatabaseTest extends FunctionalTestCase {

    protected static final String TABLE_UPLOADED = "UPLOADED";
    protected static final String TABLE_HOT_WATER = "HOTWATER";
    protected static final String TABLE_METER = "METER";
    protected static final String TABLE_WEATHER = "WEATHER";
    protected static final String TABLE_GENERATION = "GENERATION";

    protected JdbcTemplate jdbcTemplate;

    @Before
    public void before() {
        jdbcTemplate = muleContext.getRegistry().get("jdbcTemplate");
        deleteFromTestTables();
    }

    @After
    public void after() {
        deleteFromTestTables();
    }

    private void deleteFromTestTables() {
        deleteFromTestTable(TABLE_GENERATION);
        deleteFromTestTable(TABLE_HOT_WATER);
        deleteFromTestTable(TABLE_METER);
        deleteFromTestTable(TABLE_WEATHER);
        deleteFromTestTable(TABLE_UPLOADED);
    }

    protected int rowCount(String tableName) {
        List<Integer> values = jdbcTemplate.query("SELECT COUNT(*) FROM " + tableName, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        });

        return values.get(0);
    }

    private void deleteFromTestTable(String tableName) {
        jdbcTemplate.update("DELETE FROM " + tableName);
    }
}
