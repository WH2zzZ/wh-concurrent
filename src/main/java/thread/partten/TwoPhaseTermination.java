package thread.partten;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class TwoPhaseTermination extends Thread {
    volatile boolean stop = false;
    volatile ServerSocket socket;

    public static void main(String args[]) throws Exception {
        TwoPhaseTermination thread = new TwoPhaseTermination();

        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);

        System.out.println("Asking thread to stop...");
        //有时候碰到sleep,等还是需要使用打断，通知等方法
        thread.stop = true;
        thread.socket.close();

        Thread.sleep(3000);
        System.out.println("Stopping application...");
        //System.exit( 0 );
    }

    @Override
    public void run() {
        try {
            socket = new ServerSocket(7856);
        } catch (IOException e) {
            System.out.println("Could not create the socket...");
            return;
        }
        while (!stop) {
            System.out.println("Waiting for connection...");
            try {
                Socket sock = socket.accept();
            } catch (IOException e) {
                System.out.println("accept() failed or interrupted...");
            }
        }
        System.out.println("线程停止");
    }
}