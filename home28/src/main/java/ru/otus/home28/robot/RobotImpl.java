package ru.otus.home28.robot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderState;
import ru.otus.home28.order.OrderService;

import java.time.Instant;

@Slf4j
abstract class RobotImpl implements Robot {
    private TaskScheduler taskScheduler;
    private OrderService orderService;
    private Order current;

    @Autowired
    void init(TaskScheduler taskScheduler, OrderService orderService) {
        this.taskScheduler = taskScheduler;
        this.orderService = orderService;
    }

    @Override
    public synchronized void setOrder(Order order) {
        if (current != null)
            throw new RuntimeException("Robot busy");

        current = order;
        log.info("set order: {}", order);

        taskScheduler.schedule(this::onOrderComplete, Instant.now().plusSeconds(order.getDurationSec()));
    }

    @Override
    public Order currentOrder() {
        return current;
    }

    private synchronized void onOrderComplete() {
        orderService.saveOrder(current.copy().setState(OrderState.COMPLETED, "Робот закончил"), current.getState());
        current = null;
    }
}
