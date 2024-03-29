package de.demo.dao.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "de.demo.dao")
@ComponentScan({"de.demo.dao.impl", "de.demo.dao.event"})
public class DaoConfig {
}
