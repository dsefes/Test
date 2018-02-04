package main;

import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        while (true) {
            try {
                ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
                MobilePhoneRepository repo = ctx.getBean(MobilePhoneRepository.class);

                repo.save(new MobilePhone("1234", "Abbb"));

                List<MobilePhone> list = (List<MobilePhone>) repo.findAll();
                LOG.info("the list:");
                list.stream().map(MobilePhone::getNumber).forEach(LOG::debug);
                break;
            } catch (BeanCreationException e) {
                if (e.getCause() instanceof HibernateException) {
                    LOG.error("Database Exception happened", e);
                } else {
                    LOG.error("Bean creating exception happened", e);
                }
            } catch (Exception e) {
                LOG.error("Some error happened", e);
            }
        }
    }
}
