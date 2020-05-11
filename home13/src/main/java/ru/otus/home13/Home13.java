package ru.otus.home13;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class Home13 {

    public static void main(String[] args) {
        SpringApplication.run(Home13.class, args);
    }

}
