package ru.chat.network.client;

public class User1 {
    public static void main(String[] args) {
        new ClientChatAdapter ("localhost",8081);
        //new BasicChatNetwork("localhost",8080);
    }


}
