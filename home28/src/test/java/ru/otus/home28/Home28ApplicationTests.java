package ru.otus.home28;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderState;
import ru.otus.home28.order.OrderStateMachine;
import ru.otus.home28.repository.OrderRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class Home28ApplicationTests {

    @Autowired
    OrderStateMachine orderStateMachine;

    @Autowired
    OrderRepository orderRepository;

    @Test
    void doOrder() throws InterruptedException {
        var uid = orderStateMachine.create(
                new Order()
                        .setRobot(1)
                        .setPlannedDate(LocalDateTime.now().plus(1, ChronoUnit.SECONDS))
                        .setDurationSec(2)
                        .setReceipt("receiptA"),
                "test");

        assertThat(orderRepository.findById(uid)).isNotEmpty().hasValueSatisfying(o -> assertThat(o.getState()).isEqualTo(OrderState.CREATE));

        orderStateMachine.ready(uid, "test");
        assertThat(orderRepository.findById(uid).orElseThrow().getState()).isEqualTo(OrderState.READY);

        Thread.sleep(3000);
        assertThat(orderRepository.findById(uid).orElseThrow().getState()).isEqualTo(OrderState.WASH);

        Thread.sleep(2000);
        assertThat(orderRepository.findById(uid).orElseThrow().getState()).isEqualTo(OrderState.COMPLETED);
    }

}
