package ru.otus.home1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.home1.service.RunnerService;

@SpringBootApplication
@EnableConfigurationProperties
public class Main {
    public static void main(String[] args) {
        try (var context = SpringApplication.run(Main.class)) {
            var runnerService = context.getBean(RunnerService.class);
            runnerService.run();
        }
    }
}
