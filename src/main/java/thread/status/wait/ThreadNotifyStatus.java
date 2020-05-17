package thread.status.wait;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import sun.rmi.runtime.Log;
import thread.status.model.ThreadOne;
import thread.status.model.ThreadTwo;

@Slf4j
public class ThreadNotifyStatus {

    private final Object lock = new Object();

    @Test
    public void testNotify() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                log.info("{} run .... wait", Thread.currentThread().getName());
                try {
                    lock.wait();
                    log.info("{}  被唤醒了，继续执行", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.info("{} run .... wait", Thread.currentThread().getName());
                try {
                    lock.wait();
                    log.info("{}  被唤醒了，继续执行", Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t2.start();

        Thread.sleep(500);
        Thread t3 = new Thread(() -> {
            synchronized (lock) {
                log.info("{} run .... wait", Thread.currentThread().getName());
//                lock.notify();
                lock.notifyAll();
                log.info("{} 继续执行", Thread.currentThread().getName());
            }
        });
        t3.start();

        while (true){
            Thread.sleep(1000);
            log.info("{} state : {}", t1.getName(), t1.getState());
            log.info("{} state : {}", t2.getName(), t2.getState());
            log.info("{} state : {}", t3.getName(), t3.getState());
        }

    }
}
