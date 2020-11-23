package ru.otus.home22.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.otus.home22.domain.Order;

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
    /**
     * scheduled-заказы меньшие заданной даты
     */
    @Query("SELECT o FROM Order o WHERE o.scheduled = true AND o.plannedDate <= :to ORDER BY o.plannedDate")
    List<Order> getPlanned(LocalDateTime to);


}
