package com.exemple.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {
    private static final String LOG_FILE = "file.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    // Логирование потоков
    public static synchronized void log(String message){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {

            String record = "[" + LocalDateTime.now().format(FORMATTER) + "] " + message;
            writer.write(record);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка логирования: " + e.getMessage());
        }
    }
}
