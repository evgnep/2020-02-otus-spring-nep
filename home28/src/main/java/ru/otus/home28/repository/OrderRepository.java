package ru.otus.home28.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.home28.domain.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Репозитарий заказов - только чтение
 * <p>
 * !! ВНИМАНИЕ !! Не допускается менять возвращаемые заказы внутри @Transactional
 */
@Primary
public interface OrderRepository extends ReadOnlyRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    @Query("SELECT o FROM Order o WHERE o.state = ru.otus.home28.domain.OrderState.READY and o.plannedDate <= :before")
    List<Order> getReady(LocalDateTime before);
}
