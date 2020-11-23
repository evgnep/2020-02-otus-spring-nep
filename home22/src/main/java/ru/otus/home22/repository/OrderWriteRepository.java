package ru.otus.home22.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.home22.domain.Order;

import java.util.UUID;

/**
 * Внимание! Это репозиторий исключительно для нужд {@link ManagerImpl}. Все внешние пользователи пользуются
 * для изменения заказа методами {@link Manager}, а для чтения - {@link ru.topazelectro.washmanager.repository.OrderRepository}
 */
interface OrderWriteRepository extends CrudRepository<Order, UUID>, OrderRepository {
}
