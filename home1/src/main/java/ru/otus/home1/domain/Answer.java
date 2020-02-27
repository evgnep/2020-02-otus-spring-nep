package ru.otus.home1.domain;

import lombok.Data;

import java.util.List;

/**
 * Ответ на вопрос - со списком ответов или с произвольным ответом (должен соответствовать вопросу)
 */
@Data
public class Answer {
    private final Question question;
    private final String single;
    private final List<Integer> choices;
    private final boolean valid;

    public Answer(Question question, String answer) {
        this.question = question;
        this.choices = null;
        this.single = answer;
        this.valid = question.getAnswer().equals(answer);
    }

    public Answer(Question question, List<Integer> choices) {
        this.question = question;
        this.choices = choices;
        this.single = null;
        this.valid = question.isValidAnswers(choices);
    }
}
