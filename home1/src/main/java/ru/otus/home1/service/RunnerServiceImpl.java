package ru.otus.home1.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.home1.dao.QuestionDao;
import ru.otus.home1.domain.Report;

@AllArgsConstructor
@Service
public class RunnerServiceImpl implements RunnerService {
    private final ViewService viewService;
    private final QuestionDao questionDao;

    public Report run() {
        var questions = questionDao.getQuestions();
        var report = new Report(viewService.askUser());
        for (var question : questions) {
            report.getAnswers().add(viewService.askQuestion(question));
        }
        viewService.showReport(report);
        return report;
    }
}
