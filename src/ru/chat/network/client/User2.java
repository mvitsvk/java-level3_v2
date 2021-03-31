package ru.chat.network.client;

public class User2 {
    public static void main(String[] args) {
        new ClientChatAdapter ("localhost",8080);
        //new BasicChatNetwork("localhost",8080);
    }
}
