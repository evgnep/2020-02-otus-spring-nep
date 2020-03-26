package ru.otus.home1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.home1.service.RunnerService;

import java.util.Locale;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShellProcessorTest {
    @Mock
    RunnerService runnerService;
    Properties properties = new Properties(Locale.US);
    @Mock
    MessageSource messageSource;

    ShellProcessor shellProcessor;

    @BeforeEach
    public void beforeEach() {
        shellProcessor = new ShellProcessor(runnerService, properties, messageSource);
    }

    @Test
    public void test() {
        shellProcessor.test();
        verify(runnerService).run();
    }
}