package main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import timezone.GetTimeZoneRequest;
import timezone.GetTimeZoneResponse;

public class Client extends WebServiceGatewaySupport{

    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    public GetTimeZoneResponse getTimezoneByPhone(String number) {
        GetTimeZoneRequest request = new GetTimeZoneRequest();
        request.setNumber(number);

        LOG.info("Starting to process \"{}\" number", number );
        GetTimeZoneResponse response = (GetTimeZoneResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback(getDefaultUri())
        );

        LOG.info("Response received: \"{}\" timezone", response.getTime() != null ? response.getTime().getTime() : "null");
        return response;
    }
}
