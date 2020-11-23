package ru.otus.home22.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static ru.otus.home22.util.Util.oneOf;


@Data
@Entity
@Table(name = "userOrder")
@Accessors(chain = true)
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime plannedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private OrderState state = OrderState.CREATE;

    /**
     * true - заказ участвует в расписании (это можно определить и по state,
     * поле для упрощения запросов
     */
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private boolean scheduled = true;

    /**
     * true = Заказ оплачен, не исполнен и МОЖЕТ быть использован для оплаты другого заказа
     * false = Заказ оплачен, не исполнен и НЕ МОЖЕТ быть использован для оплаты другого заказа
     * null = заказ не оплачен или исполнен или запланирован
     */
    private Boolean unfulfilled;

    private int durationSec;

    @Column(nullable = false)
    private int robot;

    @Column(nullable = false)
    private BigDecimal paidCash = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal paidCard = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "userOrder", nullable = false)
    @OrderBy("date")
    @Setter(AccessLevel.NONE)
    private Collection<OrderHistory> history = new ArrayList<>();

    private String carNumber;

    public Order() {
    }

    public BigDecimal totalPaid() {
        return paidCard.add(paidCash);
    }

    public Order setState(OrderState state, String note) {
        this.state = state;
        this.scheduled = !oneOf(state, OrderState.COMPLETED, OrderState.REMOVED, OrderState.CANCELLED);
        addHistory(note);
        return this;
    }

    public void addHistory(String note) {
        var h = new OrderHistory();
        h.setState(state);
        h.setNote(note);
        history.add(h);
    }

    public void addPay(BigDecimal cash, BigDecimal card) {
        if (cash != null)
            paidCash = paidCash.add(cash);
        if (card != null)
            paidCard = paidCard.add(card);
    }

    /**
     * Проверяет требования нового заказа, возвращает первое нарушенное или null, если все ок
     */
    public String checkNew() {
        if (!history.isEmpty())
            return "history";
        if (totalPaid().compareTo(BigDecimal.ZERO) != 0)
            return "paid";
        if (plannedDate == null)
            return "plannedDate";
        if (robot == 0)
            return "robot";
        if (unfulfilled != null)
            return "unfulfilled";
        return null;
    }

}
