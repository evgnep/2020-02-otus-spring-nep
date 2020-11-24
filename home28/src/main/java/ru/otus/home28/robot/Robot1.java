package ru.otus.home28.robot;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.otus.home28.domain.Order;

@Component
@org.springframework.core.annotation.Order(1)
class Robot1 extends RobotImpl {
    public Robot1(TaskScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void checkOrder(Order order) {
        if (!order.getReceipt().equals("receiptA"))
            throw new RuntimeException("Invalid receipt");
    }
}
