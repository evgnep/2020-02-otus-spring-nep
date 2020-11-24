package ru.otus.home28.order;

import ru.otus.home28.domain.Order;

public interface RobotService {
    void checkNewOrder(Order order);

    void checkReadyOrder(Order order);
}
