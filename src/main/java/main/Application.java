package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"main"})
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // Try to start application
        while (true) {
            try {
                ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
                LOG.info("Started");
                break;
            } catch (BeanCreationException e) {
                LOG.error("Bean creating exception happened", e);
            } catch (Exception e) {
                LOG.error("Some error happened", e);
            }
        }
    }
}
