package main;

import org.springframework.data.repository.CrudRepository;

public interface MobilePhoneRepository extends CrudRepository<MobilePhone, String> {
    public MobilePhone findByNumber(String number);
}
