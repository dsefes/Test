package main;

import country.Country;
import country.GetCountryResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {
    private final MobilePhoneRepository repo;
    private final Client client;

    public MainController(MobilePhoneRepository repo, Client client) {
        this.client = client;
        this.repo = repo;
    }

    @RequestMapping(value = "/{phone}", method = RequestMethod.GET)
    public String checkCountry(@PathVariable String phone) {

        GetCountryResponse response = client.getCountryByPhone(phone);
        Country c = response.getCountry();

        MobilePhone m = new MobilePhone(phone, c.getName());
        repo.save(m);

        return m.getCountry();
    }
}
