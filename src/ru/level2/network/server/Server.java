package ru.level2.network.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private ServerSocket server;
    private Socket client;
    private DataInputStream in;
    private DataOutputStream out;
    //public final Auth authUser;
    public final SqLite authUser;
    public final Set<ListUser> listUserAuth;
    public final ConnectUser connectUser;

    public Server(int port) {
        //authUser = new Auth();
        authUser = new SqLite();
        listUserAuth = new HashSet<>();
        connectUser = new ConnectUser();

        try {
            server = new ServerSocket(port);

            System.out.println("Server is run...");
            System.out.println("Waiting Client connections.");


        } catch (IOException e) {
            throw new RuntimeException("error ServerWaitClient", e);
        }


        while (true) {
            try {

                client = server.accept();
                System.out.println("Client connect: " + client.toString());
                in = new DataInputStream(client.getInputStream());
                out = new DataOutputStream(client.getOutputStream());

                new Thread (()->{

                    doAuth(client, in, out);

                })
                    .start();

            } catch(IOException e){
                throw new RuntimeException("Client connect error");
        }
    }

    };

    private void UserSock(Socket client, DataInputStream in, String nikname) {
            String str;

            String[] strSplit;

            while (true) {

                try {
                    str = in.readUTF();
                } catch (IOException e) {
                    System.out.println("Connect close" + client.toString());
                    break;
                }

                //разобрали строку
                strSplit = str.split(" ");

                //сообщение в личку
                if (str.startsWith("/w") && !nikname.equals("")) {
                    //отрезал начало строки вырезав /w и никнейм
                    message(strSplit[1], nikname + " sey: " + str.substring(3+nikname.length()+1));
                }

                //rename user
                if (str.startsWith("/rename") && !nikname.equals("")) {
                    connectUser.RenameUser(listUserAuth, nikname, strSplit[1]);
                    message("", nikname + " rename: " + strSplit[1]);
                    authUser.RenameUser(nikname, strSplit[1]);
                    nikname = strSplit[1];
                }

                //выход пользователя
                if (str.startsWith("/exit")) {
                    message("", "User " + nikname + " exit chat.");
                    message(nikname, "/exit");
                    try {
                        connectUser.UnSubscribe(listUserAuth, nikname);
                        System.out.println("Client disconnect: " + client.toString());
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }

                    //ообщение в общий чат
                    if (!str.startsWith("/auth") && !str.startsWith("/w") && !str.startsWith("/exit") && !nikname.equals("")){
                        message("", nikname + ": " + str);
                    }
            }
    }

    public void doAuth(Socket client, DataInputStream in, DataOutputStream out){
        String str = "";
        String nikname ="";
        String[] strSplit;
        long timeStart= System.currentTimeMillis();

        while (true) {
            //ну почти 120 сек
//            дело в том что in.readUTF() вешает цикл
//            и уже не возможно во время отреагировать на время
//            я пытался обработать таймаут в сокете но чтото не полусчилось
            if (((System.currentTimeMillis()-timeStart)/1000) > 120){
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    break;
                }
            }

            try {
                str = in.readUTF();
                System.out.println(((System.currentTimeMillis()-timeStart)/1000) + ">>" + str);
            } catch (IOException e) {
                throw new RuntimeException("error auth in-read-UTF");
            }

            //разобрали строку
            strSplit = str.split(" ");

            //проверка авторизация
            if (str.startsWith("/auth")) {

            //проверили наличие
            if (authUser.AuthFind(strSplit[1], strSplit[2]) != "") {
                nikname = authUser.AuthFind(strSplit[1], strSplit[2]);
                //проверяем нет ли такого уже в списке подключенных
                if (connectUser.FindListConnectUser(listUserAuth, nikname)) {
                    connectUser.Subscribe(listUserAuth, client, in, out, nikname);
                    message("", "User " + nikname + " connect to chat.");
                    break;
                } else {
                    //отправляем личное сообщение еще не авторизованному
                    try {
                        out.writeUTF("User " + nikname + " already connected to the chat.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    out.writeUTF("User unknown");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        }
        if (nikname.length() > 0 ) UserSock(client,in, nikname);
        //return nikname;
    }

    private void message (String nikname, String message){
        if (nikname.equals("")) {
            System.out.println("> " + message);
            for (ListUser user: listUserAuth) {
                try {
                    user.getOut().writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("> " + message);
            for (ListUser user: listUserAuth) {
                try {
                    if (user.getNikname().equals(nikname)) {
                        user.getOut().writeUTF(message);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
