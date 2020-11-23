package ru.otus.home22.domain;

import java.util.stream.Stream;

import static ru.otus.home22.util.Util.oneOf;


/// enum order_state в БД
/// ВНИМАНИЕ!!! Порядок важен - см isAfter / isBefore / before / ...
public enum OrderState {
    /**
     * Заказ создан, еще не оплачен
     */
    CREATE,
    /**
     * Внесена частичная предоплата
     */
    PREPAID,
    /**
     * Полностью оплачен
     */
    PAID,
    /**
     * Полностью оплачен и прошел регистрацию. Можно задавать на робота, когда тот освободится
     */
    READY,

    /**
     * Идет задание заказа на робота
     */
    SETTING,
    /**
     * Заказ задан, ждем заезда машины
     */
    ORDER_SET,
    /**
     * Машина внутри
     */
    CAR_IN,
    /**
     * Идет мойка
     */
    WASH,
    /**
     * Мойка завершена, машина еще не выехала
     */
    COMPLETED_CAR_IN,

    /**
     * Заказ успешно завершен
     */
    COMPLETED,
    /**
     * Заказ отменен по разным причинам, была оплата или предоплата
     */
    CANCELLED,
    /**
     * Заказ отменен, оплаты НЕ было
     */
    REMOVED;

    public static OrderState[] active() {
        return new OrderState[]{CREATE, READY, SETTING, ORDER_SET, CAR_IN, WASH, COMPLETED_CAR_IN};
    }

    public static OrderState[] washing() {
        return new OrderState[]{SETTING, ORDER_SET, CAR_IN, WASH, COMPLETED_CAR_IN};
    }

    public static OrderState[] closed() {
        return new OrderState[]{COMPLETED, CANCELLED, REMOVED};
    }

    public boolean isActive() {
        return oneOf(this, CREATE, READY) || isWashing();
    }

    public boolean isWashing() {
        return oneOf(this, SETTING, ORDER_SET, CAR_IN, WASH, COMPLETED_CAR_IN);
    }

    public boolean isClosed() {
        return oneOf(this, COMPLETED, CANCELLED, REMOVED);
    }

    public OrderState[] before() {
        return Stream.of(values()).filter(s -> s.ordinal() < ordinal()).toArray(OrderState[]::new);
    }

    public OrderState[] after() {
        return Stream.of(values()).filter(s -> s.ordinal() >= ordinal()).toArray(OrderState[]::new);
    }

    public OrderState[] between(OrderState alast) {
        return Stream.of(values()).filter(s -> s.ordinal() >= ordinal() && s.ordinal() < alast.ordinal()).toArray(OrderState[]::new);
    }

    public boolean isBefore(OrderState other) {
        return ordinal() < other.ordinal();
    }

    public boolean isAfter(OrderState other) {
        return ordinal() >= other.ordinal();
    }

    public boolean isBetween(OrderState from, OrderState alast) {
        return ordinal() >= from.ordinal() && ordinal() < alast.ordinal();
    }
}

