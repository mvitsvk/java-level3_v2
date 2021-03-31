package ru.chat.network.client;

import ru.chat.network.client.gui.ChatForm;
import ru.chat.network.client.network.BasicChatNetwork;

import java.util.LinkedList;
import java.util.function.Consumer;


public class ClientChatAdapter {
    private final BasicChatNetwork network;
    private final ChatForm frame;
    private LogChat logChat;

    public ClientChatAdapter(String host, int port) {
        this.frame = new ChatForm(new Consumer<String>() {
            @Override
            public void accept(String message) {
                if (message.equals("/exit")) {
                    frame.dispose();
                }
                network.send(message);
            }
        });
        this.network = new BasicChatNetwork(host, port);
        this.logChat = new LogChat();

        receive();
    }

    private void receive() {
        new Thread(() -> {
            //first load chat
            LinkedList<String> texts = this.logChat.ReadMessage();
            for (String text: texts ) {
                frame.setTextChat(text);
            }

            while (true) {
                String message = network.receive();
                frame.setTextChat(message);
                if (message.equals("/exit")) {
                    logChat.WriteMessage(message,true);
                    break;
                }
                logChat.WriteMessage(message,false);
            }
        })
                .start();
    }

}
