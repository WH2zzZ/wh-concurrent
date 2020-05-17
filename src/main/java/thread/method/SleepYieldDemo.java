package thread.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.*;

/**
 * sleep进入的是TimedWaiting状态
 *      将没有机会执行线程之后的代码，除非当前状态变更
 * yield进入的是Runnable状态
 *      有机会执行后续代码，当cpu时间片再分配到这个线程的时候
 */
@Slf4j
public class SleepYieldDemo {

    @Test
    public void testState() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                sleep(1000);
                log.info("{} run...", currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        while (thread.getState() != State.TERMINATED){
            log.info("name:{} state:{}", thread.getName(), thread.getState());
            sleep(100);
        }
    }

    @Test
    public void testYield() throws InterruptedException {
        Thread thread = new Thread(() -> {
            log.info("{} run...", currentThread());
            yield();
            log.info("{} run...", currentThread());
        });
        thread.start();
        while (thread.getState() != State.TERMINATED){
            log.info("name:{} state:{}", thread.getName(), thread.getState());
            sleep(100);
        }
    }
}
