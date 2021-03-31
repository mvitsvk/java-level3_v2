package ru.level2.network.client;

import ru.level2.network.client.network.BasicChatNetwork;

public class User3 {
    public static void main(String[] args) {
        new BasicChatNetwork("localhost",8080);
    }
}
