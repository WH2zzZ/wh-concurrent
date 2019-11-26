package day01_thread_status.thread;

public class ThreadOne implements Runnable {

    private final String NAME = "线程1";

    public static int count = 1;

    @Override
    public void run() {
        while (true){
            System.out.println("====" + NAME + "第" + count + "次运行====");
            count++;
        }
    }
}
