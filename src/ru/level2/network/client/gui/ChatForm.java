package ru.level2.network.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ChatForm extends JFrame {
    private JTextArea textChat;
    private JTextField textSend;

    public ChatForm(Consumer<String> messageConsumer){

        setTitle("CHAT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0,0,600,400);

        new BorderLayout();

        JPanel talk = new JPanel(new BorderLayout());
        talk.setBackground(Color.GRAY);

        textChat = new JTextArea();
        talk.add(textChat,BorderLayout.CENTER);
        textChat.setFont(new Font("Arial", Font.PLAIN, 20));
        textChat.setEditable(false);

        JPanel sey = new JPanel(new BorderLayout());
        sey.setBackground(Color.LIGHT_GRAY);

        textSend = new JTextField();
        sey.add(textSend, BorderLayout.CENTER);
        textSend.setFont(new Font("Arial", Font.PLAIN, 20));

        ChatListener chatListener = new ChatListener(textSend, textChat, messageConsumer);

        textSend.addActionListener(chatListener);

        JButton textButton = new JButton();
        sey.add(textButton, BorderLayout.EAST);
        textButton.setFont(new Font("Arial", Font.PLAIN, 20));
        textButton.setText("SEND");
        textButton.addActionListener(chatListener);

        add(talk,BorderLayout.CENTER);
        add(sey,BorderLayout.SOUTH);

        setVisible(true);
    }

    public void setTextChat(String  text) {
        this.textChat.append(text+"\n");

    }
}
