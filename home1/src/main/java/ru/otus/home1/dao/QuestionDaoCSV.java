package ru.otus.home1.dao;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.home1.domain.Question;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Читает вопросы из CSV-файла из ресурсов
 * Формат файла:
 * первая строка (заголовок) - игнорируется
 * все остальные строки - вопросы
 * <p>
 * Первый столбец - сам вопрос
 * Второй столбец - фиксированный ответ
 * Третий столбец - признак правильного варианта (true/false)
 * Четвертый столбец - описание варианта и т.п.
 * <p>
 * Пример:
 * Вопрос;Ответ;Вариант 1 верный;Вариант 1
 * Автор Java;Гослинг
 * private совместим с;;false;abstract;true;final;true;static
 */
@Repository
public class QuestionDaoCSV implements QuestionDao {
    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoCSV(@Value("${application.questions}") String csvResourceName, Locale locale) throws IOException {
        String name = csvResourceName + (locale == null ? "" : "_" + locale.toString()) + ".csv";
        try (var stream = getClass().getResourceAsStream(name)) {
            boolean first = true;
            for (var record : CSVFormat.EXCEL.withDelimiter(';').parse(new InputStreamReader(stream, "windows-1251"))) {
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

    private Question parse(CSVRecord record) {
        var size = record.size();

        var question = new Question();
        question.setText(record.get(0));
        if (size < 3 || record.get(2).isEmpty()) {
            question.setAnswer(record.get(1));
        } else {
            for (int i = 2; i < size && !record.get(i).isEmpty(); i += 2) {
                question.addChoice(record.get(i + 1), Boolean.parseBoolean(record.get(i)));
            }
        }

        return question;
    }
}
