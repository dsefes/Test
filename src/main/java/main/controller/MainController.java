package main.controller;

import main.service.ITimezoneService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    private final ITimezoneService timezoneService;

    public MainController(ITimezoneService timezoneService) {
        this.timezoneService = timezoneService;
    }

    @RequestMapping(value = "/{phone}", method = RequestMethod.GET)
    public String checkTimezone(@PathVariable String phone) {
        return timezoneService.getTimezoneByPhoneNumber(phone);
    }
}
