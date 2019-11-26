package day01_thread;

import org.junit.Test;

import java.util.Date;

/**
 *
 */
public class Demo01 extends Thread {

    public Demo01(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("开始执行：" + new Date());
        }
        // 我要休息10秒钟，亲，不要打扰我哦
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("线程被终止了");
        }

        System.out.println("结束执行：" + new Date());
    }

    @Test
    public static void main(String[] args) throws InterruptedException {
        Demo01 d1 = new Demo01("first-thread");

        Demo01 d2 = new Demo01("second-thread");

        //守护线程
        d1.setDaemon(false);
        d2.setDaemon(false);

        d1.start();
        d2.start();
    }

}
