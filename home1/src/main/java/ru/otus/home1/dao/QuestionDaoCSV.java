package ru.otus.home1.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import ru.otus.home1.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Читает вопросы из CSV-файла из ресурсов
 * Формат файла:
 * первая строка (заголовок) - игнорируется
 * все остальные строки - вопросы
 *
 * Первый столбец - сам вопрос
 * Второй столбец - фиксированный ответ
 * Третий столбец - признак правильного варианта (true/false)
 * Четвертый столбец - описание варианта и т.п.
 *
 * Пример:
 * Вопрос;Ответ;Вариант 1 верный;Вариант 1
 * Автор Java;Гослинг
 * private совместим с;;false;abstract;true;final;true;static
 */
public class QuestionDaoCSV implements QuestionDao {
    private final List<Question> questions = new ArrayList<>();

    private Question parse(CSVRecord record) {
        var size = record.size();

        var question = new Question();
        question.setText(record.get(0));
        if (size < 3) {
            question.setAnswer(record.get(1));
        }
        else {
            for (int i = 2; i < size; i += 2)
                question.addChoice(record.get(i + 1), Boolean.parseBoolean(record.get(i)));
        }

        return question;
    }

    public QuestionDaoCSV(String csvResourceName) throws IOException {
        try(var stream = getClass().getResourceAsStream(csvResourceName)) {
            boolean first = true;
            for (var record : CSVFormat.DEFAULT.parse(new InputStreamReader(stream))) {
                if (first) {
                    first = false;
                } else {
                    questions.add(parse(record));
                }
            }
        }
    }

    @Override
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }
}
