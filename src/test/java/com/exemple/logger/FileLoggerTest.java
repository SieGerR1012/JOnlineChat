package com.exemple.logger;

import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class FileLoggerTest {
    private static final String LOG_FILE = "file.log";

    @Test
    void shouldWriteLogToFile() throws Exception {

        String message = "test log message";
        FileLogger.log(message);

        List<String> lines = Files.readAllLines(Path.of(LOG_FILE));

        assertFalse(lines.isEmpty(), "Log-файл не должен быть пустым");
        String lastLine = lines.get(lines.size() - 1);
        assertTrue(lastLine.contains(message), "Последняя запись должна содержать сообщение");
    }
}
