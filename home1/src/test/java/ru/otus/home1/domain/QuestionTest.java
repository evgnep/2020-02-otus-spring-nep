package ru.otus.home1.domain;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class QuestionTest {
    @Test
    public void customQuestion() {
        var q = new Question();
        q.setText("2+2?");
        q.setAnswer("4");

        assertThat(q.isTest()).isFalse();
        assertThat(q.getAnswer().equals("4"));
        assertThatThrownBy(() -> q.isValidAnswers(List.of(0, 1))).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void testQuestion() {
        var q = new Question();
        q.setText("2+2?");
        q.addChoice("3", false);
        q.addChoice("4", true);
        q.addChoice("b10", true);

        assertThat(q.isTest()).isTrue();
        assertThatThrownBy(() -> q.getAnswer()).isInstanceOf(UnsupportedOperationException.class);
        assertThat(q.isValidAnswers(List.of(1, 2))).isTrue();
        assertThat(q.isValidAnswers(List.of(0, 1, 2))).isFalse();
        assertThat(q.isValidAnswers(List.of(2))).isFalse();
    }
}