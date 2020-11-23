package ru.otus.home22;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Home22Application {

    public static void main(String[] args) {
        SpringApplication.run(Home22Application.class, args);
    }

}
