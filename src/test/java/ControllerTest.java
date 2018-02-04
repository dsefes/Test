import main.Client;
import main.controller.MainController;
import main.dao.TimezoneDao;
import main.service.ITimezoneService;
import main.service.TimezoneService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ws.client.WebServiceIOException;
import timezone.GetTimeZoneResponse;
import timezone.Timezone;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {
    private MainController mainController;
    private ITimezoneService timezoneService;

    @Mock
    private Client client;
    @Mock
    private TimezoneDao timezoneDao;

    String testTimezone = "+1234";
    String testTimezoneIANA = "Test/TimezoneIANA";

    @Before
    public void setUp() {
        timezoneService = new TimezoneService(client, timezoneDao);
        mainController = new MainController(timezoneService);
    }

    @Test
    public void knownTimezoneTest() throws Exception {
        Timezone t = new Timezone() {{
            setTime(testTimezone);
        }};
        GetTimeZoneResponse getTimeZoneResponse = new GetTimeZoneResponse() {{
            setTime(t);
        }};

        Mockito.when(client.getTimezoneByPhone(Matchers.anyString())).thenReturn(getTimeZoneResponse);
        Mockito.when(timezoneDao.getTimeZoneIANA(Matchers.matches("^\\+12.*$"))).thenReturn(testTimezoneIANA);
        String res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(testTimezoneIANA));
    }

    @Test
    public void serviceUnavailableTest() throws Exception {
        Mockito.when(client.getTimezoneByPhone(Matchers.anyString())).thenThrow(WebServiceIOException.class);
        String res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(TimezoneService.IO_ERROR));
    }

    @Test
    public void serverErrorTest() throws Exception {
        Mockito.when(client.getTimezoneByPhone(Matchers.anyString())).thenThrow(Exception.class);
        String res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(TimezoneService.SERVER_ERROR));
    }

    @Test
    public void unknownTimezoneTest() throws Exception {
        GetTimeZoneResponse getTimeZoneResponse = new GetTimeZoneResponse() {{
            setTime(null);
        }};
        Mockito.when(client.getTimezoneByPhone(Matchers.anyString())).thenReturn(getTimeZoneResponse);
        String res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(TimezoneService.UNKNOWN_TIMEZONE));
    }

    @Test
    public void notFoundTimezoneTest() throws Exception {

        Timezone t = new Timezone() {{
            setTime(testTimezone);
        }};
        GetTimeZoneResponse getTimeZoneResponse = new GetTimeZoneResponse() {{
            setTime(t);
        }};
        Mockito.when(client.getTimezoneByPhone(Matchers.anyString())).thenReturn(getTimeZoneResponse);
        Mockito.when(timezoneDao.getTimeZoneIANA(Matchers.anyString())).thenReturn(null);
        String res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(TimezoneService.NOT_PARSED_TIMEZONE));
        Mockito.when(timezoneDao.getTimeZoneIANA(Matchers.anyString())).thenReturn("");
        res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(TimezoneService.NOT_PARSED_TIMEZONE));
    }

    @Test
    public void unavailableDBTest() throws Exception {
        Timezone t = new Timezone() {{
            setTime(testTimezone);
        }};
        GetTimeZoneResponse getTimeZoneResponse = new GetTimeZoneResponse() {{
            setTime(t);
        }};
        Mockito.when(client.getTimezoneByPhone(Matchers.anyString())).thenReturn(getTimeZoneResponse);
        Mockito.when(timezoneDao.getTimeZoneIANA(Matchers.anyString())).thenThrow(new DataAccessException("") {
        });
        String res = mainController.checkTimezone("1234");
        Assert.assertThat(res, CoreMatchers.is(t.getTime()));
    }
}
