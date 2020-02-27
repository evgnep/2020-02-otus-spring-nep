package ru.otus.home1.service;

import ru.otus.home1.domain.Answer;
import ru.otus.home1.domain.Question;
import ru.otus.home1.domain.Report;
import ru.otus.home1.domain.User;

import java.util.List;
import java.util.stream.Collectors;

public class ViewServiceConsole implements ViewService {
    @Override
    public User askUser() {
        System.out.print("Укажите ваше имя: ");
        var name = System.console().readLine();
        System.out.println();

        System.out.print("Укажите вашу фамилию: ");
        var surname = System.console().readLine();
        System.out.println();

        return new User(name, surname);
    }

    @Override
    public Answer askQuestion(Question question) {
        System.out.println(question.getText());
        if (question.isTest()) {
            for (int i = 0; i < question.getChoicesSize(); ++i) {
                System.out.println(i + 1 + ": " + question.getChoice(i));
            }
            System.out.println("Введите номера верных вариантов через запятую: ");
            var answer = System.console().readLine();
            System.out.println();

            var choices = List.of(answer.split(",")).stream().map(a -> Integer.parseInt(a.strip()) - 1).collect(Collectors.toList());
            return new Answer(question, choices);
        }
        else {
            System.out.println("Введите ответ: ");
            var answer = System.console().readLine();
            System.out.println();

            return new Answer(question, answer);
        }
    }

    @Override
    public void showReport(Report report) {
        System.out.println("Тест завершен");
        System.out.println("Процент правильных ответов: " + report.score());
    }
}
