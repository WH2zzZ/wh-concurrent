package thread.status.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author WangHan
 * @Create 2020/5/26 5:15 下午
 */
@Slf4j
public class ParkDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                log.info("执行:{}", Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("锁住");
                LockSupport.park();
            }
        });
        thread.start();

        Thread.sleep(1000);
        log.info("唤醒");
        LockSupport.unpark(thread);
        log.info("唤醒");
        LockSupport.unpark(thread);
        log.info("唤醒");
        LockSupport.unpark(thread);

    }
}
