package ru.otus.home22.util;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * Вспомогательный класс для игнорирования исключений
 */
@Slf4j
public class IgnoreException {
    public static void call(Runnable runnable, String note, Logger logger) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (logger == null)
                logger = log;
            if (note == null)
                note = "Подавляем исключение";

            logger.error(note, e);
        }
    }
}
