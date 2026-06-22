package com.exemple;

import com.exemple.server.ChatServer;

public class ServerMain {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }
}