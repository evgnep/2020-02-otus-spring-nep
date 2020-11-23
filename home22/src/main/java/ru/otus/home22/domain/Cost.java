package ru.otus.home22.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.home22.util.BigDecimals;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * Длительность и цена рецепта/операции/etc
 */
@Embeddable
@Data
@AllArgsConstructor
public class Cost {
    private BigDecimal price;
    @Column(name = "duration")
    private Integer durationSec;   // в секундах

    public Cost() {
    }

    public Cost(Cost other) {
        price = other.getPrice();
        durationSec = other.getDurationSec();
    }

    public Duration getDuration() {
        return durationSec == null ? null : Duration.ofSeconds(durationSec);
    }

    /// мерж не null значений из other
    public void update(Cost other) {
        if (other != null) {
            if (other.getDurationSec() != null)
                durationSec = other.getDurationSec();
            if (other.getPrice() != null)
                price = other.getPrice();
        }
    }

    /// вычитание b по месту
    public void substract(Cost b) {
        if (b != null) {
            if (b.price != null)
                price = price.subtract(b.price);
            if (b.durationSec != null)
                durationSec -= b.durationSec;
        }
    }

    /// сложение b по месту
    public void add(Cost b) {
        if (b != null) {
            if (b.price != null)
                price = price.add(b.price);
            if (b.durationSec != null)
                durationSec += b.durationSec;
        }
    }

    @Override
    public boolean equals(Object b) {
        if (!(b instanceof Cost))
            return false;
        var c = (Cost) b;
        return BigDecimals.equalsValue(price, c.price) && Objects.equals(durationSec, c.durationSec);
    }

    @Override
    public int hashCode() {
        return Objects.hash(durationSec, BigDecimals.hashCodeValue(price));
    }

    @Override
    public String toString() {
        return "price=" + price + ", duration=" + durationSec;
    }
}
