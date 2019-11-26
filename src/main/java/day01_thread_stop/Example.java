package day01_thread_stop;

import day01_thread.Demo01;
import org.junit.Test;

import java.util.Date;

/**
 *
 */
public class Example extends Thread {

    public Example(String name) {
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

    public static void main(String[] args) {
        Demo01 d1 = new Demo01("first-thread");

        d1.start();
        // 你超过三秒不醒过来，我就干死你
        try {
            Thread.sleep(3000);
//            d1.stop(); //会强制中止线程,即使还有未完成的任务,这可能导致资源未施放等
            d1.interrupt(); //会中止线程,但是会让线程把当前还没完成的任务做完,这就很可能导致并没有真正的关闭线程,需要线程里面加入一个判断标识,来时刻判断是否真的线程被中断掉了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
