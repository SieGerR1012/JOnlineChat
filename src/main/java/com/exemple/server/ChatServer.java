package com.exemple.server;

import com.exemple.config.SettingsLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {
    private final int port;

    // Список подключенных клиентов
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public ChatServer() {
        this.port = SettingsLoader.getPort();
    }

    public void start() {
        int port = SettingsLoader.getPort();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);

            while (true) {
                System.out.println("Ожидание подключения клиента...");

                Socket socket = serverSocket.accept();
                System.out.println("Подключен клиент: " + socket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(socket, this);

                clients.add(clientHandler);
                clientHandler.start();
            }

        } catch (IOException e) {
            System.out.println("Ошибка сервера: " + e.getMessage());
        }
    }

    // Рассылка сообщений клиентам
    public void broadcast(String message) {

        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
        System.out.println("Отправлено всем: " + message);
    }

    // Удаление клиента после отключения
    public void removeClient(
            ClientHandler clientHandler) {
        clients.remove(clientHandler);
        System.out.println("Клиент отключен");
    }
}
