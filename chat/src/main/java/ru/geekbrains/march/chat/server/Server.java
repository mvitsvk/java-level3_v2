package ru.geekbrains.march.chat.server;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private int port;
    private List<ClientHandler> clients;
    private AuthenticationProvider authenticationProvider;
    private ExecutorService executorService;

    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    public Server(int port) {
        LOGGER.info("init server start");
        this.port = port;
        this.clients = new ArrayList<>();
        this.authenticationProvider = new InMemoryAuthenticationProvider();
        this.executorService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.debug("Сервер запущен на порту " + port);
            while (true) {
                LOGGER.info("Ждем нового клиента..");
                Socket socket = serverSocket.accept();
                LOGGER.debug("socket: " + socket);
                LOGGER.info("Клиент подключился");
                new ClientHandler(this, socket, executorService);
            }
        } catch (IOException e) {
            LOGGER.throwing(Level.ERROR,e);
        }
        finally {
            LOGGER.info("server shutdown");
            executorService.shutdown();
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        LOGGER.info("Клиент " + clientHandler.getUsername() + " вошел в чат");
        broadcastMessage("Клиент " + clientHandler.getUsername() + " вошел в чат");
        broadcastClientsList();
        LOGGER.debug("broadcastClientsList exec");
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        LOGGER.info("Клиент " + clientHandler.getUsername() + " вышел из чата");
        broadcastMessage("Клиент " + clientHandler.getUsername() + " вышел из чата");
        broadcastClientsList();
        LOGGER.debug("broadcastClientsList exec");
    }

    public synchronized void broadcastMessage(String message)  {
        LOGGER.debug("start broadcastMessage:" + message);
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
            LOGGER.trace("broadcastMessage sendMessage " + clientHandler.getUsername());
        }
        LOGGER.debug("end broadcastMessage.");
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String receiverUsername, String message) {
        LOGGER.debug("sendPrivateMessage sendMessage " + sender.getUsername() + " to " + receiverUsername + " sey:" + message);
        for (ClientHandler c : clients) {
            LOGGER.trace("sendPrivateMessage find receiverUsername " + c.getUsername());
            if (c.getUsername().equals(receiverUsername)) {
                LOGGER.trace("sendPrivateMessage receiverUsername " + c.getUsername() + " FIND");
                c.sendMessage("От: " + sender.getUsername() + " Сообщение: " + message);
                sender.sendMessage("Пользователю: " + receiverUsername + " Сообщение: " + message);
                return;
            }
        }
        sender.sendMessage("Невозможно отправить сообщение пользователю: " + receiverUsername + ". Такого пользователя нет в сети.");
        LOGGER.info("Невозможно отправить сообщение пользователю: " + receiverUsername + ". Такого пользователя нет в сети.");
    }

    public synchronized boolean isUserOnline(String username) {
        LOGGER.debug("isUserOnline find" + username);
        for (ClientHandler clientHandler : clients) {
            LOGGER.trace("isUserOnline find " + username + " clientHandler: " + clientHandler.getUsername());
            if (clientHandler.getUsername().equals(username)) {
                LOGGER.trace("isUserOnline " + username + " FIND");
                return true;
            }
        }
        LOGGER.trace("isUserOnline " + username + " NOT FIND");
        return false;
    }

    public synchronized void broadcastClientsList() {
        LOGGER.debug("broadcastClientsList");
        StringBuilder stringBuilder = new StringBuilder("/clients_list ");
        LOGGER.trace("broadcastClientsList " + stringBuilder.toString());
        for (ClientHandler c : clients) {
            stringBuilder.append(c.getUsername()).append(" ");
            LOGGER.trace("broadcastClientsList " + stringBuilder.toString());
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        String clientsList = stringBuilder.toString();
        LOGGER.trace("broadcastClientsList " + stringBuilder.toString());
        for (ClientHandler clientHandler : clients) {
            LOGGER.trace("clientsList sendMessage to client: " + clientHandler.getUsername());
            clientHandler.sendMessage(clientsList);
        }
    }
}
