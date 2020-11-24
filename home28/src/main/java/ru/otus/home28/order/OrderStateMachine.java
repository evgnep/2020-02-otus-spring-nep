package ru.otus.home28.order;

import ru.otus.home28.domain.Order;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderStateMachine {
    UUID create(Order order, String source);
    void ready(UUID id, String source);
    void cancel(UUID id, String reason, boolean unfulfilled, String source);
    void reschedule(UUID id, LocalDateTime dateTime, String source);
    void remove(UUID id, String source);
}
