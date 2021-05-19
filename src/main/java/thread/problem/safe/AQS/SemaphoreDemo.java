package thread.problem.safe.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Author WangHan
 * @Create 2020/5/24 3:21 下午
 */
@Slf4j
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //获得信号量,获取不到会循环等待
                    semaphore.acquire();
                    log.info("线程:{}，占用一个信号量", Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    log.info("线程:{}，释放一个信号量", Thread.currentThread().getName());
                    semaphore.release();
                }

            }).start();
        }
    }
}
