package main;


import country.Country;
import country.GetCountryRequest;
import country.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class Client extends WebServiceGatewaySupport{
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public GetCountryResponse getCountryByPhone(String number) {
        GetCountryRequest request = new GetCountryRequest();
        request.setNumber(number);

        LOG.info("Starting to process \"{}\" number", number );
        GetCountryResponse response = (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(
                request,
                new SoapActionCallback("http://localhost:8080/country-phone")
        );
        LOG.info("Response received: \"{}\" country", response.getCountry().getName() );
        return response;
    }

    public void printResponse(GetCountryResponse response) {
        Country country = response.getCountry();

        if (country != null) {
            LOG.info("Country is " + country.getName() + ", ");
        } else {
            LOG.info("No country received");
        }
    }
}
