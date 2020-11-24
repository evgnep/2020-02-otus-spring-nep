package ru.otus.home28.order;

import ru.otus.home28.domain.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Интерфейс к менеджеру заказов для внешних пользователей
 * <p>
 * Во всех "изменяющих" методах {@code source} - инициатор вызова метода (терминал, мобильное приложение, ...)
 *
 * @see ManagerForProcessor - для внутренних пользователей
 * @see ru.topazelectro.washmanager.repository.OrderRepository - чтение заказов
 */
public interface OrderStateMachine {
    /**
     * Создание заказа и постановка в очередь
     * Заказ будет в состоянии CREATE
     */
    UUID create(Order order, String source);

    /**
     * Оплата заказа налом или картой. Если оплата полностью, то заказ станет PAID, иначе - PREPAID
     * Возможна в состояниях CREATE, PREPAID
     */
    void pay(UUID id, BigDecimal cash, BigDecimal card, String source);

    /**
     * Клиент готов мыться
     * PAID -> READY
     */
    void ready(UUID id, String source);

    /**
     * Отмена заказа
     * PREPAID | PAID | READY | SET -> CANCELLED
     * CREATE | REMOVED -> REMOVED
     *
     * @param unfulfilled true - оплата сохраняется и может быть использована для оплаты чеком. false - оплата сгорает
     */
    void cancel(UUID id, String reason, boolean unfulfilled, String source);

    /**
     * Изменение плановой даты мойки. Возможно в состояниях PREPAID / PAID
     * Состояние не меняется
     */
    void reschedule(UUID id, LocalDateTime dateTime, String source);

    /**
     * Удаление заказа без оплаты
     * CREATE | REMOVED -> REMOVED
     */
    void remove(UUID id, String source);
}
