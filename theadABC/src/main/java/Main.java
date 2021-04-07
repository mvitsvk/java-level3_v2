import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final Object mon = new Object();
    private char curL = 'A';

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Main waitNotifyApp = new Main();

        for (int i = 65; i < 68; i++) {
            char ch = (char) i;
            executor.execute(() -> {
                    waitNotifyApp.printLetter(ch);
            });

        }
        executor.shutdown();
    }

    public void printLetter(char letter) {

        synchronized (mon){
            try{

            for (int j = 0; j < 5; j++) {
                while (curL != letter) {
                    mon.wait();
                }
                System.out.print(letter);

                if (curL == 'A') {
                    curL = 'B';
                } else if (curL == 'B') {
                    curL = 'C';
                } else if (curL == 'C') {
                    curL = 'A';
                    System.out.println();
                }

                mon.notifyAll();
            }

            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
