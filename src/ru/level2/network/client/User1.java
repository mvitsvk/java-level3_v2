package ru.level2.network.client;

//import ru.level2.network.client.network.BasicChatNetwork;

public class User1 {
    public static void main(String[] args) {
        new ClientChatAdapter ("localhost",8081);
        //new BasicChatNetwork("localhost",8080);
    }


}
