package ru.otus.home1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.home1.service.RunnerService;

import java.util.Locale;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext()) {
            context.register(Main.class);
            context.refresh();

            var runnerService = context.getBean(RunnerService.class);
            runnerService.run();
        }
    }

    @Bean
    public MessageSource messageSource() {
        var ms = new ResourceBundleMessageSource();
        ms.setBasename("/messages");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public Locale locale(@Value("${application.locale}") Locale locale) {
        return locale;
    }
}
