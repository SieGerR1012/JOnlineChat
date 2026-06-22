package com.exemple.config;

import org.testng.annotations.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoaderTest {
    @Test
    void shouldLoadPortFromSettings() {

        int port = SettingsLoader.getPort();

        assertTrue(port > 0, "Порт должен быть положительным");
        assertEquals(8080, port, "Порт должен совпадать с settings.txt");
    }
}
