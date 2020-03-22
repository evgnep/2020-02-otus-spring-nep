package ru.otus.home1.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.home1.Properties;
import ru.otus.home1.domain.Answer;
import ru.otus.home1.domain.Question;
import ru.otus.home1.domain.Report;
import ru.otus.home1.domain.User;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class ViewServiceConsole implements ViewService {
    private final MessageSource messageSource;
    private final Properties properties;
    private final Scanner scanner;
    private final PrintStream outputStream;

    public ViewServiceConsole(MessageSource messageSource, Properties properties) {
        this.messageSource = messageSource;
        this.properties = properties;
        scanner = new Scanner(properties.getInputStream());
        outputStream = properties.getOutputStream();
    }

    private String msg(String key, Object... args) {
        if (properties.getDefaultLocale() == null)
            return key + Arrays.toString(args);
        return messageSource.getMessage(key, args, properties.getDefaultLocale());
    }

    @Override
    public User askUser() {
        outputStream.print(msg("msg.enterName"));
        var name = scanner.nextLine();
        outputStream.println();

        outputStream.print(msg("msg.enterSurname"));
        var surname = scanner.nextLine();
        outputStream.println();

        return new User(name, surname);
    }

    @Override
    public Answer askQuestion(Question question) {
        outputStream.println(question.getText());
        if (question.isTest()) {
            for (int i = 0; i < question.getChoicesSize(); ++i) {
                outputStream.println(i + 1 + ": " + question.getChoice(i));
            }
            outputStream.println(msg("msg.enterTest"));
            var answer = scanner.nextLine();
            outputStream.println();

            var choices = List.of(answer.split(",")).stream().map(a -> Integer.parseInt(a.strip()) - 1).collect(Collectors.toList());
            return new Answer(question, choices);
        } else {
            outputStream.println(msg("msg.enterAnswer"));
            var answer = scanner.nextLine();
            outputStream.println();

            return new Answer(question, answer);
        }
    }

    @Override
    public void showReport(Report report) {
        outputStream.println(msg("msg.testComplete"));
        outputStream.println(msg("msg.testResult", report.score()));
    }
}
