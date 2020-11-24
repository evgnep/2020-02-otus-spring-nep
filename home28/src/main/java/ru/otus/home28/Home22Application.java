package ru.otus.home28;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.config.EnableIntegration;
import ru.otus.home28.domain.Order;
import ru.otus.home28.order.OrderStateMachine;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
@EnableJpaRepositories
@EnableIntegration
@RequiredArgsConstructor
public class Home22Application implements CommandLineRunner {

    private final OrderStateMachine orderStateMachine;

    public static void main(String[] args) {
        SpringApplication.run(Home22Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var uid = orderStateMachine.create(
                new Order()
                        .setRobot(1)
                        .setPlannedDate(LocalDateTime.now().plus(1, ChronoUnit.HOURS))
                        .setReceipt("receiptA"),
                "main");
    }
}
