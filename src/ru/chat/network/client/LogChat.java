package ru.chat.network.client;

import java.io.*;
import java.util.LinkedList;

public class LogChat {
    private LinkedList<String> messageBuffWrite;
    private LinkedList<String> messageBuffRead;

    public LogChat() {
        this.messageBuffWrite = new LinkedList<>();
        this.messageBuffRead = new LinkedList<>();
    }

    public LinkedList<String> ReadMessage(){
        try {
            FileReader fileIn = new FileReader("chat.log");
            BufferedReader buffFileIn = new BufferedReader(fileIn);
            while (buffFileIn.ready()) {
                this.messageBuffRead.add(buffFileIn.readLine());
                if (this.messageBuffRead.size() > 100 ) this.messageBuffRead.remove(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.messageBuffRead;
    }

    public void WriteMessage(String message, boolean status){
        int size = 10;
        this.messageBuffWrite.add (message);
        //every close program
        if (status) size = -1;
        if (this.messageBuffWrite.size() > size ){
            try {
                FileWriter fileOut = new FileWriter("chat.log", true);
                for (String messB : messageBuffWrite) {
                        fileOut.write(messB+"\n");
                }
                messageBuffWrite.clear();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
