package thread.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.*;

/**
 * 调用interrupt()方法仅仅是在当前线程中打了一个停止的标记，并不是真正的停止线程
 *
 * interrupted()测试当前线程是否已经是中断状态，执行后具有清除中断状态flag的功能
 *
 * isInterrupted()测试线程Thread对象是否已经是中断状态，但不清除中断状态flag
 *
 */
@Slf4j
public class InterruptDemo {

    /**
     * interrupt 打断sleep状态，并且sleep会抛出InterruptedException异常，然后清除打断状态
     * 可以打断sleep,wait,join
     */
    @Test
    public void testInterruptedSleep() {
        Thread thread = new Thread(() -> {
            log.info("{} run...", currentThread());
            log.info("{} sleep .... ", currentThread());
            try {
                sleep(10000);
//                wait();
            } catch (InterruptedException e) {
                log.warn("{} 被打断了...", currentThread());
            }
            while (true) {
                log.info("{} run...", currentThread());
            }
        });
        thread.start();

        log.info("{} interrupte state", thread.getName());
        thread.interrupt();
        log.info("{} interrupt", thread.getName());
        log.info("{} interrupt state", thread.getName());
    }

    /**
     * 打断正常运行的
     */
    @Test
    public void testInterruptedNormal() {
        Thread thread = new Thread(() -> {
            while (true) {
                log.info("{} run...", Thread.currentThread());
            }
        });
        thread.start();

        log.info("{} interrupt state {}", thread.getName(), thread.isInterrupted());

        thread.interrupt();
        log.info("{} interrupt", thread.getName());
        //可以看出，打断后不会立即停止线程运行，而是告诉线程需要打断，然后由线程自己去决定结束，看下面例子
        log.info("{} thread state {}", thread.getName(), thread.getState());
        log.info("{} interrupt state {}", thread.getName(), thread.isInterrupted());
    }

    /**
     * 调用interrupt()方法仅仅是在当前线程中打了一个停止的标记，并不是真正的停止线程
     * interrupted()测试当前线程是否已经是中断状态，执行后具有清除中断状态flag的功能
     * isInterrupted()测试线程Thread对象是否已经是中断状态，但不清除中断状态flag
     *
     */
    @Test
    public void testInterruptAndInterrupted() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " i = " + i);

                if (i == 5) {
                    Thread.currentThread().interrupt();
                    System.out.println("interrupted 1: " + Thread.interrupted());
                    System.out.println("interrupted 2: " + Thread.interrupted());
                }
            }
        });

        thread.start();
    }

    /**
     * 两阶段终止模式
     */
    @Test
    public void testTwoPhaseTermination() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    log.info("{} 识别到被打断，处理后事，停止当前线程", Thread.currentThread());
                    break;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //由于sleep的时候被打断，打断标记会被删除，所以需要在这里再打断一次
                    Thread.currentThread().interrupt();
                    log.info("{} 打断啦", Thread.currentThread());
                }
                log.info("{} run...", Thread.currentThread());
            }
        });
        thread.start();

        Thread.sleep(3000);
        thread.interrupt();
    }

}
