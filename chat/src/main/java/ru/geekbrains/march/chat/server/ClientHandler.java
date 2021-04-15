package ru.geekbrains.march.chat.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class ClientHandler {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public String getUsername() {
        return username;
    }

    public ClientHandler(Server server, Socket socket, ExecutorService executorService) throws IOException {
        LOGGER.debug("init executorService.execute");
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        LOGGER.trace("server=" + this.server + " socket=" + this.socket + " IN=" + this.in + " OUT=" + this.out);
        executorService.execute(() -> {
            try {
                while (true) { // Цикл авторизации
                    String msg = in.readUTF();
                    LOGGER.trace("while messages" + msg);
                    if (msg.startsWith("/login ")) {
                        LOGGER.debug("find /login");
                        // /login Bob 100xyz
                        String[] tokens = msg.split("\\s+");
                        LOGGER.trace("login: "+ tokens.toString());
                        if (tokens.length != 3) {
                            LOGGER.debug("/login_failed Введите имя пользователя и пароль");
                            sendMessage("/login_failed Введите имя пользователя и пароль");
                            continue;
                        }
                        String login = tokens[1];
                        String password = tokens[2];
                        LOGGER.debug("getAuthenticationProvider" + login);
                        String userNickname = server.getAuthenticationProvider().getNicknameByLoginAndPassword(login, password);
                        LOGGER.debug("userNickname " + userNickname);
                        if (userNickname == null) {
                            LOGGER.debug("userNickname == null");
                            sendMessage("/login_failed Введен некорретный логин/пароль");
                            continue;
                        }
                        if (server.isUserOnline(userNickname)) {
                            LOGGER.debug("isUserOnline " + userNickname);
                            sendMessage("/login_failed Учетная запись уже используется");
                            continue;
                        }
                        username = userNickname;
                        sendMessage("/login_ok " + username);
                        LOGGER.debug("login_ok" + username);
                        server.subscribe(this);
                        break;
                    }
                }

                while (true) { // Цикл общения с клиентом
                    String msg = in.readUTF();
                    LOGGER.trace("while messages" + msg);
                    if (msg.startsWith("/")) {
                        LOGGER.debug("find executeCommand");
                        executeCommand(msg);
                        continue;
                    }
                    server.broadcastMessage(username + ": " + msg);
                    LOGGER.debug("broadcastMessage " + username + ": " + msg);
                }
            } catch (IOException e) {
                LOGGER.throwing(Level.ERROR, e);
            } finally {
                disconnect();
            }
        });
    }

    private void executeCommand(String cmd) {
        // /w Bob Hello, Bob!!!
        LOGGER.info("executeCommand");
        if (cmd.startsWith("/w ")) {
            LOGGER.debug("executeCommand /w");
            String[] tokens = cmd.split("\\s+", 3);
            if (tokens.length != 3) {
                LOGGER.debug("executeCommand error");
                LOGGER.trace("executeCommand error" + tokens);
                sendMessage("Server: Введена некорректная команда");
                return;
            }
            server.sendPrivateMessage(this, tokens[1], tokens[2]);
            LOGGER.trace("sendPrivateMessage " + tokens[1] +" "+ tokens[2]);
            return;
        }

        // /change_nick myNewNickname
        if (cmd.startsWith("/change_nick ")) {
            LOGGER.debug("executeCommand /change_nick");
            String[] tokens = cmd.split("\\s+");
            if (tokens.length != 2) {
                LOGGER.debug("executeCommand error");
                LOGGER.trace("executeCommand error" + tokens);
                sendMessage("Server: Введена некорректная команда");
                return;
            }
            String newNickname = tokens[1];
            LOGGER.trace("newNickname" + tokens[1]);
            if (server.isUserOnline(newNickname)) {
                LOGGER.debug("Server: Такой никнейм уже занят");
                sendMessage("Server: Такой никнейм уже занят");
                return;
            }
            server.getAuthenticationProvider().changeNickname(username, newNickname);
            LOGGER.debug("changeNickname" + username + " " + newNickname);
            username = newNickname;
            sendMessage("Server: Вы изменили никнейм на " + newNickname);
            server.broadcastClientsList();
        }
    }

    public void sendMessage(String message) {
        try {
            LOGGER.info("sendMessage" + message);
            out.writeUTF(message);
            LOGGER.debug("out.writeUTF " +message);
        } catch (IOException e) {
            LOGGER.throwing(Level.ERROR, e);
            disconnect();
        }
    }

    public void disconnect() {
        LOGGER.info("user disconnect");
        server.unsubscribe(this);
        if (socket != null) {
            LOGGER.debug("socket not null");
            try {
                socket.close();
                LOGGER.debug("socket close");
            } catch (IOException e) {
                LOGGER.throwing(Level.ERROR, e);
            }
        }
    }
}
