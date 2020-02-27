package ru.otus.home1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сдающий тест
 */
@Data
@AllArgsConstructor
public class User {
    private final String name;
    private final String surname;
}
