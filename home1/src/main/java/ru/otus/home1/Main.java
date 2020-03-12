package ru.otus.home1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.home1.service.RunnerService;

@SpringBootApplication
@EnableConfigurationProperties
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(Main.class);
            context.refresh();

            var runnerService = context.getBean(RunnerService.class);
            runnerService.run();
        }
    }

//    @Bean
//    public MessageSource messageSource() {
//        var ms = new ResourceBundleMessageSource();
//        ms.setBasename("messages");
//        ms.setDefaultEncoding("UTF-8");
//        return ms;
//    }
}
