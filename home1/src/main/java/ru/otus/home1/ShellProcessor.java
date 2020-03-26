package ru.otus.home1;


import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.home1.domain.Report;
import ru.otus.home1.service.RunnerService;

@ShellComponent
public class ShellProcessor {
    private final RunnerService runnerService;
    private final Properties properties;
    private final MessageSource messageSource;
    private Report lastReport;

    public ShellProcessor(RunnerService runnerService, Properties properties, MessageSource messageSource) {
        this.runnerService = runnerService;
        this.properties = properties;
        this.messageSource = messageSource;
    }

    private String msg(String key, Object... args) {
        return messageSource.getMessage(key, args, properties.getDefaultLocale());
    }

    @ShellMethod("Пройти тест")
    public void test() {
        lastReport = runnerService.run();
    }

    @ShellMethod("Посмотреть отчет")
    public String report() {
        if (lastReport == null) {
            return msg("msg.runTest");
        } else {
            return msg("msg.testResult", lastReport.score());
        }
    }

}
