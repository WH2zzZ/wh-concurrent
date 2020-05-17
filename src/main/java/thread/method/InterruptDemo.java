package thread.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.*;

/**
 * isInterrupt 是否被打断,不会清楚打断标记
 *
 * interrupted 是否被打断,注意会清楚打断标记
 */
@Slf4j
public class InterruptDemo {

    /**
     * interrupt 打断sleep状态，并且sleep会抛出InterruptedException异常，然后清除打断状态
     * 可以打断sleep,wait,join
     */
    @Test
    public void testInterruptedSleep(){
        Thread thread = new Thread(() -> {
            log.info("{} run...", currentThread());
            log.info("{} sleep .... ", currentThread());
            try {
                sleep(10000);
//                wait();
            } catch (InterruptedException e) {
                log.warn("{} 被打断了...", currentThread());
            }
            while (true){
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
    public void testInterruptedNormal(){
        Thread thread = new Thread(() -> {
            while (true){
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
     * 两阶段终止模式
     */
    @Test
    public void testTwoPhaseTermination() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
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
