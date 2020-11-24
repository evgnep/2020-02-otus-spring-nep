package ru.otus.home28.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.home28.domain.Order;

import java.util.UUID;

/**
 * Внимание! Это репозиторий исключительно для нужд {@link ManagerImpl}. Все внешние пользователи пользуются
 * для изменения заказа методами {@link Manager}, а для чтения - {@link ru.topazelectro.washmanager.repository.OrderRepository}
 */
public interface OrderWriteRepository extends CrudRepository<Order, UUID>, OrderRepository {
}
