package day01_thread;

public class Demo03 {

    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run() {
                System.out.println("thread start ...");
            }
        }.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 start ...");
            }
        }).start();
    }
}
