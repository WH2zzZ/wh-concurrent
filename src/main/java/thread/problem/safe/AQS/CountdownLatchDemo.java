package thread.problem.safe.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * @Author WangHan
 * @Create 2020/5/24 4:33 下午
 */
@Slf4j
public class CountdownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(() -> {
            try {
                log.info("run thread:{}", Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("count -- :{}", Thread.currentThread().getName());
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            try {
                log.info("run thread:{}", Thread.currentThread().getName());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("count -- :{}", Thread.currentThread().getName());
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            try {
                log.info("run thread:{}", Thread.currentThread().getName());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("count -- :{}", Thread.currentThread().getName());
            countDownLatch.countDown();
        }).start();

        new Thread(() -> {
            try {
                log.info("run thread:{}", Thread.currentThread().getName());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("count -- :{}", Thread.currentThread().getName());
            countDownLatch.countDown();
        }).start();

        log.info("wait thread:{}", Thread.currentThread().getName());
        //只有当countDownLatch计数减到0才会被唤醒
        countDownLatch.await();
        log.info("end thread:{}", Thread.currentThread().getName());
    }
}
