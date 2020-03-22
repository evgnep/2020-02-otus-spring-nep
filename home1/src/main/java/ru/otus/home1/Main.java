package ru.otus.home1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.otus.home1.service.RunnerService;

import java.util.Locale;

@SpringBootApplication
@EnableConfigurationProperties
public class Main {
    public static void main(String[] args) {
        try (var context = SpringApplication.run(Main.class)) {
            var runnerService = context.getBean(RunnerService.class);
            runnerService.run();
        }
    }

    @Bean
    public Locale locale(@Value("${application.locale}") Locale locale) {
        return locale;
    }
}
