package com.exemple.server;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final Socket socket;
    private final ChatServer server;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientHandler(Socket socket, ChatServer server) {

        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (true) {

                String message = reader.readLine();

                if (message == null) {
                    break;
                }

                System.out.println("Получено: " + message);
                server.broadcast(message);
            }

        } catch (IOException e) {
            System.out.println("Клиент отключился");

        } finally {

            server.removeClient(this);

            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
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
