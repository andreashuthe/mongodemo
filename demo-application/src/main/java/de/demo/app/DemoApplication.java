package de.demo.app;

import de.demo.app.conf.AppConfig;
import de.demo.app.conf.sec.SecConfig;
import de.demo.dao.conf.DaoConfig;
import de.demo.service.conf.ServiceConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SuppressWarnings("unused")
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        new SpringApplicationBuilder(DemoApplication.class)
                .sources(AppConfig.class, SecConfig.class, DaoConfig.class, ServiceConfig.class)
                .build()
                .run();
    }

}
