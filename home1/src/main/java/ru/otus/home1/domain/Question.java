package ru.otus.home1.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Вопрос
 * Может быть с выбором из вариантов (isTest) или со свободным ответом (!isTest)
 */
@Data
public class Question {
    private String text;

    @Getter(AccessLevel.NONE)
    private final List<Choice> choices = new ArrayList<>();

    public Question() {}

    public Question(String text, String answer) {
        setText(text);
        setAnswer(answer);
    }

    public void setAnswer(String text) {
        if (!choices.isEmpty())
            throw new UnsupportedOperationException("Это тест");
        choices.add(new Choice(text, true));
    }

    public String getAnswer() {
        if (isTest())
            throw new UnsupportedOperationException("Это тест");
        return choices.get(0).text;
    }

    public void addChoice(String text, boolean valid) {
        choices.add(new Choice(text, valid));
    }

    public boolean isTest() {
        return choices.size() > 1;
    }

    public String getChoice(int i) {
        return choices.get(i).text;
    }

    public int getChoicesSize() {
        return choices.size();
    }

    public boolean isValidAnswers(List<Integer> answers) {
        if (!isTest())
            throw new UnsupportedOperationException("Это не тест");

        // все ли отмеченные варианты верные
        for (var no : answers)
            if (!this.choices.get(no).valid)
                return false;

        // все ли верные варианты отмечены
        for (int no = 0, size = this.choices.size(); no < size; ++no)
            if (this.choices.get(no).valid && answers.indexOf(no) == -1)
                return false;

        return true;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class Choice {
        final String text;
        final boolean valid;
    }
}
