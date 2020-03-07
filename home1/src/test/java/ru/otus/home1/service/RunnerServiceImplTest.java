package ru.otus.home1.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.home1.dao.QuestionDao;
import ru.otus.home1.domain.Answer;
import ru.otus.home1.domain.Question;
import ru.otus.home1.domain.Report;
import ru.otus.home1.domain.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class RunnerServiceImplTest {
    QuestionDao questionDao = mock(QuestionDao.class);
    ViewService viewService = mock(ViewService.class);

    RunnerService runnerService = new RunnerServiceImpl(viewService, questionDao);

    Question q1 = new Question("q1", "1");
    Question q2;
    User user = new User("Ivan", "Ivanov");

    @BeforeEach
    public void before() {
        q2 = new Question();
        q2.setText("q2");
        q2.addChoice("0", false);
        q2.addChoice("1", true);
    }

    @Test
    public void run() {
        var report = new Report[1];

        when(questionDao.getQuestions()).thenReturn(List.of(q1, q2));

        when(viewService.askUser()).thenReturn(user);
        when(viewService.askQuestion(eq(q1))).thenReturn(new Answer(q1, "1"));
        when(viewService.askQuestion(eq(q2))).thenReturn(new Answer(q2, List.of(0, 1)));
        doAnswer(invocation -> report[0] = invocation.getArgument(0, Report.class)).
                when(viewService).showReport(any());

        runnerService.run();

        assertThat(report[0].getUser()).isSameAs(user);
        assertThat(report[0].score()).isEqualTo(50);
    }
}
