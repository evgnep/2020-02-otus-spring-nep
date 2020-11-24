package ru.otus.home28.robot;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import ru.otus.home28.domain.Order;

@Component
@org.springframework.core.annotation.Order(2)
class Robot2 extends RobotImpl {
    public Robot2(TaskScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void checkOrder(Order order) {
    }
}
