package main.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TimezoneDaoImpl implements TimezoneDao {
    public static final String ADD_QUERY = "INSERT INTO myrep VALUES(?, ?)";
    public static final String GET_TIMEZONE_QUERY = "SELECT timezone_iana FROM myrep WHERE timezone=?";

    private final JdbcTemplate jdbcTemplate;

    public TimezoneDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getTimeZoneIANA(String number) {
        String result = "";
        List<Map<String, Object>> dbres = jdbcTemplate.queryForList(GET_TIMEZONE_QUERY, number);
        if (dbres.size() > 0) {
            result = dbres.get(0).get("timezone_iana").toString();
        }
        return result;
    }
}
