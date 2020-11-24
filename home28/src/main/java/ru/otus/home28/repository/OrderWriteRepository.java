package ru.otus.home28.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.home28.domain.Order;

import java.util.UUID;

/**
 * Внимание! Это репозиторий исключительно для внутренних нужд пакета order. Все внешние пользователи пользуются
 * для изменения заказа методами {@link ru.otus.home28.order.OrderService}, а для чтения - {@link OrderRepository}
 */
public interface OrderWriteRepository extends CrudRepository<Order, UUID>, OrderRepository {
}
