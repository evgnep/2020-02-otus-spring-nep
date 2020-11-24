package ru.otus.home28.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.home28.domain.Order;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class OrderStateMachineImpl implements OrderStateMachine {

    private final EntryPoint orderCreate;

    @Override
    @Transactional
    public synchronized UUID create(Order order, String source) {
        return orderCreate.create(order, source);
    }

    @Override
    public void pay(UUID id, BigDecimal cash, BigDecimal card, String source) {

    }

    @Override
    public void ready(UUID id, String source) {

    }

    @Override
    public void cancel(UUID id, String reason, boolean unfulfilled, String source) {

    }

    @Override
    public void reschedule(UUID id, LocalDateTime dateTime, String source) {

    }

    @Override
    public void remove(UUID id, String source) {

    }
}
