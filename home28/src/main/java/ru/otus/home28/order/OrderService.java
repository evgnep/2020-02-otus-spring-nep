package ru.otus.home28.order;

import ru.otus.home28.domain.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderService {
    void checkNew(Order o);

    UUID saveOrder(Order o);

    void checkTimeSlot(Order o, LocalDateTime time);
}
