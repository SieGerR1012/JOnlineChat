package com.exemple.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private final String host;
    private final int port;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private String username;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start(){
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Scanner scanner = new Scanner(System.in);

            // Ввод имени пользователя
            System.out.print("Введите имя: ");
            username = scanner.nextLine();

            // Отправка имени на сервер
            writer.write(username);
            writer.newLine();
            writer.flush();

            // Запуск потока чтения сообщений от сервера
            new Thread(new ClientReceiver(reader)).start();
            System.out.println("Чат запущен. Введите /exit для выхода.");

            // Основной цикл отправки сообщений
            while (true) {
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
            stop();

        } catch (IOException e) {
            System.out.println("Ошибка клиента: " + e.getMessage());
        }
    }

    // Остановка клиента
    private void stop() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
        System.out.println("Вы вышли из чата");
    }
}
