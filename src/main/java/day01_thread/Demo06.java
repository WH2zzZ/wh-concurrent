package day01_thread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 */
public class Demo06 {

    public static void main(String[] args) {

        ExecutorService threadPool = Executors.newCachedThreadPool();
//        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

        threadPool.shutdown();
    }
}
