package main;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by danil on 04.02.18.
 */
@Entity
public class MobilePhone {
    @Id
    private String number;

    private String country;

    protected MobilePhone() {

    }
    protected MobilePhone(String number, String country) {
        this.number = number;
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
