package ru.level2.network.client;

import ru.level2.network.client.network.BasicChatNetwork;

public class User2 {
    public static void main(String[] args) {
        new ClientChatAdapter ("localhost",8080);
        //new BasicChatNetwork("localhost",8080);
    }
}
