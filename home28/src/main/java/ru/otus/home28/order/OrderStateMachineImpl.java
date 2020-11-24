package ru.otus.home28.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.home28.domain.Order;
import ru.otus.home28.repository.OrderRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class OrderStateMachineImpl implements OrderStateMachine {

    private final EntryPoint orderCreate;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public synchronized UUID create(Order order, String source) {
        return orderCreate.create(order, source);
    }

    @Override
    @Transactional
    public synchronized void ready(UUID id, String source) {
        var order = orderRepository.findById(id).orElseThrow();
        orderCreate.ready(order, source);
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
