package ru.otus.home28.robot;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import ru.otus.home28.domain.Order;

import java.time.Instant;

@RequiredArgsConstructor
abstract class RobotImpl implements Robot {
    private final TaskScheduler taskScheduler;
    private Order current;

    @Override
    public synchronized void setOrder(Order order) {
        if (current != null)
            throw new RuntimeException("Robot busy");
        current = order;

        taskScheduler.schedule(this::onOrderComplete, Instant.now().plusSeconds(order.getDurationSec()));
    }

    @Override
    public Order currentOrder() {
        return current;
    }

    private synchronized void onOrderComplete() {
        current = null;
    }
}
