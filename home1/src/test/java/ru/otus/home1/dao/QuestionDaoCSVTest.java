package ru.otus.home1.dao;

import org.junit.Test;
import ru.otus.home1.domain.Question;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class QuestionDaoCSVTest {
    @Test
    public void parse() throws IOException {
        var q1 = new Question("Создатель Java", "Гослинг");
        var q2 = new Question();
        q2.setText("2+2");
        q2.addChoice("b11", true);
        q2.addChoice("3", false);
        q2.addChoice("4", true);

        var questions = new QuestionDaoCSV("/test.csv");
        assertThat(questions.getQuestions())
                .containsExactly(q1, q2);
    }

}