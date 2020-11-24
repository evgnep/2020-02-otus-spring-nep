package ru.otus.home28.robot;

import org.springframework.stereotype.Component;
import ru.otus.home28.domain.Order;

@Component
@org.springframework.core.annotation.Order(2)
class Robot2 extends RobotImpl {
    @Override
    public void checkOrder(Order order) {
    }
}
