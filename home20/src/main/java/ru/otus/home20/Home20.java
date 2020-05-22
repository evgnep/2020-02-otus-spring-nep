package ru.otus.home20;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Home20 {

    public static void main(String[] args) {
        SpringApplication.run(Home20.class, args);
    }

}
