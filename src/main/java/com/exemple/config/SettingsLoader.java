package com.exemple.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class SettingsLoader {
    private static final String SETTINGS_FILE = "src/main/resources/settings.txt";

    public static int getPort() {

        Properties properties = new Properties();

        try {
            properties.load(Files.newInputStream(Path.of(SETTINGS_FILE)));

            return Integer.parseInt(properties.getProperty("port"));

        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать настройки", e);
        }
    }
}