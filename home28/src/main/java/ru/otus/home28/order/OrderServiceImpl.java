package ru.otus.home28.order;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.otus.home28.domain.Order;
import ru.otus.home28.domain.OrderHistory;
import ru.otus.home28.domain.OrderState;
import ru.otus.home28.repository.OrderWriteRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderWriteRepository orderRepository;
    private final ApplicationEventPublisher publisher;
    private final EntryPoint entryPoint;

    @Override
    public void checkNew(Order o) {
        String error;
        if (!o.getHistory().isEmpty())
            error = "history";
        else if (o.totalPaid().compareTo(BigDecimal.ZERO) != 0)
            error = "paid";
        else if (o.getPlannedDate() == null)
            error = "plannedDate";
        else if (o.getRobot() == 0)
            error = "robot";
        else if (o.getUnfulfilled() != null)
            error = "unfulfilled";
        else
            return;

        throw new RuntimeException("Ошибка при создании заказа: " + error);
    }

    @Override
    public UUID saveOrder(Order o) {
        var history = o.getHistory().toArray(OrderHistory[]::new);
        var prevState = history.length > 1 ? history[history.length - 1].getState() : OrderState.NOT_EXISTS;
        o = orderRepository.save(o);
        if (o.getState() != prevState)
            publisher.publishEvent(new StateChangeEvent(o, OrderState.NOT_EXISTS));
        return o.getId();
    }

    @Override
    public void checkTimeSlot(Order o, LocalDateTime time) {

    }

    @TransactionalEventListener
    public void onStateChange(StateChangeEvent event) {
        entryPoint.stateChanged(event.order, event.previous);
    }

    @AllArgsConstructor
    static class StateChangeEvent {
        final Order order;
        final OrderState previous;
    }
}
