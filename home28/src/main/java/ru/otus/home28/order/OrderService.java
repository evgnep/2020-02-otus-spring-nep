package ru.otus.home28.order;

import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderState;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderService {
    void checkNew(Order o);

    UUID saveOrder(Order o, OrderState prev);

    void checkTimeSlot(Order o, LocalDateTime time);
}
