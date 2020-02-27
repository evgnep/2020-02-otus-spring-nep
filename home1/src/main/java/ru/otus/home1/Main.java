package ru.otus.home1;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.home1.service.RunnerService;

public class Main {
    public static void main(String[] args) {
        try (var context = new ClassPathXmlApplicationContext("/spring-context.xml")) {
            var runnerService = context.getBean(RunnerService.class);
            runnerService.run();
        }
    }
}
