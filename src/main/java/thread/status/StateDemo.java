package thread.status;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Author WangHan
 * @Create 2020/5/10 6:00 下午
 */
@Slf4j
public class StateDemo {

    @Test
    public void testAllState() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.info("{} run", Thread.currentThread().getName());
        });

        Thread t2 = new Thread(() -> {
            log.info("{} run", Thread.currentThread().getName());
            while (true){
            }
        });
        t2.start();


        Thread t3 = new Thread(() -> {
            log.info("{} run", Thread.currentThread().getName());
        });
        t3.start();

        Thread t4 = new Thread(() -> {
            log.info("{} run", Thread.currentThread().getName());
            synchronized (StateDemo.class){
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                }
            }
        });
        t4.start();

        Thread t5 = new Thread(() -> {
            log.info("{} run", Thread.currentThread().getName());
            synchronized (StateDemo.class){
                try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                }
            }
        });
        t5.start();

        Thread t6 = new Thread(() -> {
            log.info("{} run", Thread.currentThread().getName());
            try {
                t2.join();
            } catch (InterruptedException e) {
            }
        });
        t6.start();

        Thread.sleep(1000);
        log.info("{} thread. state:{}", t1.getName(), t1.getState());
        log.info("{} thread. state:{}", t2.getName(), t2.getState());
        log.info("{} thread. state:{}", t3.getName(), t3.getState());
        log.info("{} thread. state:{}", t4.getName(), t4.getState());
        log.info("{} thread. state:{}", t5.getName(), t5.getState());
        log.info("{} thread. state:{}", t6.getName(), t6.getState());
    }
}
