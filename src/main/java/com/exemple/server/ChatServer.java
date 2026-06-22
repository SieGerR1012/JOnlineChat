package com.exemple.server;

import com.exemple.config.SettingsLoader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    // Список подключенных клиентов
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final Set<String> usernames = ConcurrentHashMap.newKeySet();

    public void start() {
        int port = SettingsLoader.getPort();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);

            while (true) {
                System.out.println("Ожидание подключения клиента...");

                Socket socket = serverSocket.accept();
                System.out.println("Подключен клиент: " + socket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(socket, this);

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

    // Регистрация клиента после проверки имени
    public void registerClient(ClientHandler clientHandler) {
        clients.add(clientHandler);
        usernames.add(clientHandler.getUsername());
    }

    // Проверка, занято ли имя
    public boolean isUsernameTaken(String username) {
        return usernames.contains(username);
    }

    // Удаление клиента после отключения
    public void removeClient(
            ClientHandler clientHandler) {
        clients.remove(clientHandler);
        usernames.remove(clientHandler.getUsername());
        System.out.println("Клиент отключен");
    }
}
