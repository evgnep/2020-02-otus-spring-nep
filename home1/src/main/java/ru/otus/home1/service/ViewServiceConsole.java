package ru.otus.home1.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.home1.domain.Answer;
import ru.otus.home1.domain.Question;
import ru.otus.home1.domain.Report;
import ru.otus.home1.domain.User;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class ViewServiceConsole implements ViewService {
    private final MessageSource messageSource;
    private final Locale locale;
    private final Scanner scanner;

    public ViewServiceConsole(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
        scanner = new Scanner(System.in);
    }

    private String msg(String key, Object... args) {
        return messageSource.getMessage(key, args, locale);
    }

    @Override
    public User askUser() {
        System.out.print(msg("msg.enterName"));
        var name = scanner.nextLine();
        System.out.println();

        System.out.print(msg("msg.enterSurname"));
        var surname = scanner.nextLine();
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
            System.out.println(msg("msg.enterTest"));
            var answer = scanner.nextLine();
            System.out.println();

            var choices = List.of(answer.split(",")).stream().map(a -> Integer.parseInt(a.strip()) - 1).collect(Collectors.toList());
            return new Answer(question, choices);
        } else {
            System.out.println(msg("msg.enterAnswer"));
            var answer = scanner.nextLine();
            System.out.println();

            return new Answer(question, answer);
        }
    }

    @Override
    public void showReport(Report report) {
        System.out.println(msg("msg.testComplete"));
        System.out.println(msg("msg.testResult", report.score()));
    }
}
