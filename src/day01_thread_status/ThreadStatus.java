package day01_thread_status;

import day01_thread_status.thread.ThreadOne;
import day01_thread_status.thread.ThreadTwo;
import org.junit.Test;

public class ThreadStatus {

    private final ThreadOne threadOne = new ThreadOne();
    private final ThreadTwo threadTwo = new ThreadTwo();

    /**
     * 初始化状态
     */
    @Test
    public void threadInit(){
        Thread thread = new Thread(threadOne);
    }

    /**
     * 等待
     */
    @Test
    public void threadWait(){
        Thread thread1 = new Thread(threadOne);

        Thread thread2 = new Thread(threadTwo);

        thread1.start();
        thread2.start();
        while (true) {
            System.out.println("主线程执行了");
            threadOneWaitWhenCountIs10(thread1);
            threadOneNotifyWhenThreadTwoCountIs100(thread1);
        }
    }

    private synchronized void threadOneNotifyWhenThreadTwoCountIs100(Thread thread1) {
        while (ThreadTwo.count > 1000000000) {
            synchronized (thread1) {
                System.out.println("唤醒thread1, ThreadTwo is " + ThreadTwo.count);
                thread1.notify();
            }
        }
    }

    private void threadOneWaitWhenCountIs10(Thread thread1) {
        while (ThreadOne.count > 10) {
            synchronized (thread1) {
                try {
                    System.out.println("thread1陷入等待");
                    System.out.println("ThreadOne is " + ThreadOne.count);
                    System.out.println("ThreadTwo is " + ThreadTwo.count);
                    thread1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
