package thread.create;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * 继承thread 跑一个线程，无返回值
 */
@Slf4j
public class Demo01 extends Thread {

    /**
     * 重写构造方法，自定义线程名
     * @param name
     */
    public Demo01(String name) {
        super(name);
    }

    @Override
    public void run() {
        //不中断就去执行
        while (!isInterrupted()) {
            log.info("开始执行：{}", new Date());
        }
        //一但发现终端，看中断的线程是否可以sleep,然后结束
        try {
            //如果出错，说明线程被终端了，所以当前线程是不可以sleep的
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("线程被终止了");
        }
        log.info("结束执行:{}", new Date());
    }

    @Test
    public static void main(String[] args) {
        Demo01 d1 = new Demo01("first-thread");
        //守护线程
        //做一些后台操作，当主线程执行完毕，守护线程不管有没有执行完毕，也要终止
        d1.setDaemon(false);
        d1.start();

        try {
            Thread.sleep(2000);
            d1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
