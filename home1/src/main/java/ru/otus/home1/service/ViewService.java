package ru.otus.home1.service;

import ru.otus.home1.domain.Answer;
import ru.otus.home1.domain.Question;
import ru.otus.home1.domain.Report;
import ru.otus.home1.domain.User;

public interface ViewService {
    User askUser();
    Answer askQuestion(Question question);
    void showReport(Report report);
}
