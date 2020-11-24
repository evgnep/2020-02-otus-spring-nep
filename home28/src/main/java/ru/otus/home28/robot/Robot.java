package ru.otus.home28.robot;

import ru.otus.home28.domain.Order;

public interface Robot {
    void checkOrder(Order order);

    void setOrder(Order order);

    Order currentOrder();
}
