package com.exemple.client;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите порт сервера: ");
        int port = scanner.nextInt();
        scanner.nextLine();

        ChatClient client = new ChatClient("localhost", port);
        client.start();
    }
}
