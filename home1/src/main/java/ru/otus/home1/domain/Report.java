package ru.otus.home1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Отчет о результатах
 */
@Data
@AllArgsConstructor
public class Report {
    private final User user;
    private final List<Answer> answers = new ArrayList<>();

    /**
     * Процент правильных ответов
     */
    public int score() {
        if (answers.size() == 0)
            return 0;

        return (int)answers.stream().filter(Answer::isValid).count() * 100 / answers.size();
    }
}
