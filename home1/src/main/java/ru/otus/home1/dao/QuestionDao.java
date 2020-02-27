package ru.otus.home1.dao;

import ru.otus.home1.domain.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestions();
}
