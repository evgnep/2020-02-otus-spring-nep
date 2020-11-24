package ru.otus.home28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableIntegration
@EnableScheduling
public class Home28Application {
    public static void main(String[] args) {
        SpringApplication.run(Home28Application.class, args);
    }
}
