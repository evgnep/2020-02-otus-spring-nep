package ru.otus.home22.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

/**
 * История заказа
 * Связь с заказом - только на стороне заказа (OneToMany)
 */
@Data
@Entity
@Table
public class OrderHistory {
    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "orderState")
    private OrderState state;

    @Column(nullable = false)
    private String note;
}
