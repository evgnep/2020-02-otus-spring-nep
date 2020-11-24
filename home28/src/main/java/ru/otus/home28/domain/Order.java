package ru.otus.home28.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@Data
@Entity
@Table(name = "userOrder")
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
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

    private int durationSec;

    @Column(nullable = false)
    private int robot;

    @Column(nullable = false)
    private String receipt;

    @Column(nullable = false)
    private BigDecimal paidCash = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal paidCard = BigDecimal.ZERO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
        addHistory(note);
        return this;
    }

    public void addHistory(String note) {
        var h = new OrderHistory();
        h.setState(state);
        h.setNote(note);
        history.add(h);
    }

    public Order copy() {
        return toBuilder().build();
    }
}
