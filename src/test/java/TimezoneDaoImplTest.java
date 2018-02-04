import main.dao.TimezoneDao;
import main.dao.TimezoneDaoImpl;
import main.Application;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(properties = {"soap-address=0"})
public class TimezoneDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimezoneDao timezoneDao;

    @Test
    public void getTimeZoneTest() {
        String testTimezone = "+00321";
        String testTimezoneIANA = "Test timezone";
        jdbcTemplate.update(TimezoneDaoImpl.ADD_QUERY, testTimezone, testTimezoneIANA);

        String timezone = timezoneDao.getTimeZoneIANA(testTimezone);
        Assert.assertThat(timezone, CoreMatchers.is(testTimezoneIANA));
    }

    @Test
    public void getTimeZoneByUnknownNumberTest() {
        String actual = timezoneDao.getTimeZoneIANA("1234");
        Assert.assertThat(actual, CoreMatchers.is(""));
    }
}
