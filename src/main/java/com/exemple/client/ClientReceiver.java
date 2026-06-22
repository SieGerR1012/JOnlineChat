package com.exemple.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver implements Runnable {
    private final BufferedReader reader;

    public ClientReceiver(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String message;

            // Прослушка сервера
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Соединение закрыто");
        }
    }
}
