package ru.otus.home1;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties("application")
@Data
@Component
public class Properties {
    private String questions;
    private Locale locale;
}
