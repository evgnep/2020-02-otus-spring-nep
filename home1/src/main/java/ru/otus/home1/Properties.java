package ru.otus.home1;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

@Component
@Data
@AllArgsConstructor
public class Properties {
    private final Locale defaultLocale;
    private final InputStream inputStream;
    private final PrintStream outputStream;

    @Autowired
    public Properties(@Value("${application.locale}") Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
        inputStream = System.in;
        outputStream = System.out;
    }
}
