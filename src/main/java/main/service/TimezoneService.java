package main.service;

import main.Client;
import main.dao.TimezoneDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceIOException;
import timezone.GetTimeZoneResponse;
import timezone.Timezone;

@Component
public class TimezoneService implements ITimezoneService {

    private static final Logger LOG = LoggerFactory.getLogger(TimezoneService.class);

    public final static String UNKNOWN_TIMEZONE = "Unknown timezone";
    public final static String NOT_PARSED_TIMEZONE = "Timezone could not be parsed";
    public final static String IO_ERROR = "Service is unavailable. Try later...";
    public final static String SERVER_ERROR = "Service error occured...";

    private Client client;
    private final TimezoneDao timezoneDao;

    public TimezoneService(Client client, TimezoneDao timezoneDao) {
        this.client = client;
        this.timezoneDao = timezoneDao;
    }

    public String getTimezoneByPhoneNumber(String number) {
        GetTimeZoneResponse response;
        try {
            response = client.getTimezoneByPhone(number);
        } catch (WebServiceIOException e) {
            LOG.error("Error getting the timezone: connection problems.", e);
            return IO_ERROR;
        } catch (Exception e) {
            LOG.error("Some error happened.", e);
            return SERVER_ERROR;
        }

        Timezone t = response.getTime();

        if (t == null) {
            LOG.error("Timezone could not be received.");
            return UNKNOWN_TIMEZONE;
        }

        String timezone = t.getTime();
        try {
            timezone = timezoneDao.getTimeZoneIANA(timezone);
        } catch (DataAccessException e) {
            LOG.error("DB connection error. Return timezone fetched from SOAP.", e);
        }

        if (timezone == null || timezone.isEmpty()) {
            LOG.error("Timezone " + t.getTime() + " could not be parsed.");
            timezone = NOT_PARSED_TIMEZONE;
        }

        return timezone;
    }
}
