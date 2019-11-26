package day01_thread;

/**
 * 这种方式创建的线程其实创建的是一个线程任务,并非一个线程
 */
public class Demo02 implements Runnable{

    @Override
    public void run() {
        while (true){
            System.out.println("thread running ...");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Demo02());
        //这个时候会调用Demo02的run方法
        thread.start();
    }
}
