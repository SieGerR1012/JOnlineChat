package com.exemple.server;

import com.exemple.logger.FileLogger;
import com.exemple.util.TimeUtil;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final ChatServer server;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;
    private boolean registered;

    public ClientHandler(Socket socket, ChatServer server) {

        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Первое сообщение от клиента — имя
            username = reader.readLine();

            if (server.isUsernameTaken(username)) {
                writer.write("Ошибка: имя \"" + username + "\" уже занято");
                writer.newLine();
                writer.flush();
                return;
            }

            server.registerClient(this);
            registered = true;
            System.out.println(username + " вошел в чат");
            FileLogger.log(username + " вошел в чат");

            while (true) {

                String message = reader.readLine();

                // Клиент вышел
                if (message == null) {
                    break;
                }

                // Обработка выхода клиента
                if (message.equalsIgnoreCase("/exit")) {
                    System.out.println(username + " вышел из чата");
                    FileLogger.log(username + " вышел из чата");
                    server.broadcast(username + " покинул чат");

                    break;
                }

                String formattedMessage = "[" + TimeUtil.now() + "] " + username + ": " + message;

                System.out.println("Получено: " + formattedMessage);
                FileLogger.log(formattedMessage);

                server.broadcast(formattedMessage);
            }

        } catch (IOException e) {
            System.out.println("Клиент отключился");

        } finally {
            if (registered) {
                FileLogger.log(username + " покинул чат");
                server.broadcast(username + " покинул чат");
                server.removeClient(this);
            }

            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    public String getUsername() {
        return username;
    }

    //Отправка сообщения клиенту
    public void sendMessage(String message) {

        try {
            writer.write(message);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            System.out.println("Ошибка отправки");
        }
    }
}
