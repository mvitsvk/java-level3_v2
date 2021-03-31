package ru.chat.network.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class ChatListener implements ActionListener {
    private JTextField textSend;
    private JTextArea textChat;
    private Consumer<String> consumer;

    public ChatListener (JTextField textSend, JTextArea textChat, Consumer<String> consumer){
        this.textSend = textSend;
        this.textChat = textChat;
        this.consumer = consumer;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.textChat.append(this.textSend.getText()+"\n");
        consumer.accept(this.textSend.getText());
        this.textSend.setText("");


    }
}
