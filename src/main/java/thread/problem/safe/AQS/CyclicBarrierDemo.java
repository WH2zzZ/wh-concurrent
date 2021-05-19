package thread.problem.safe.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author WangHan
 * @Create 2020/5/24 5:07 下午
 */
@Slf4j
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> log.info("任务结束啦"));

//        for (int i = 0; i < 3; i++) {
            threadPool.submit(() -> {
                log.info("task start. thread:{}", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                    log.info("thread:{} wait:{}", Thread.currentThread().getName(), cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                    log.info("task run. thread:{} wait:{}", Thread.currentThread().getName(), cyclicBarrier.getNumberWaiting());
                    log.info("task end. thread:{}", Thread.currentThread().getName());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            threadPool.submit(() -> {
                log.info("task start. thread:{}", Thread.currentThread().getName());
                try {
                    Thread.sleep(2000);
                    log.info("thread:{} wait:{}", Thread.currentThread().getName(), cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                    log.info("task run. thread:{} wait:{}", Thread.currentThread().getName(), cyclicBarrier.getNumberWaiting());
                    log.info("task end. thread:{}", Thread.currentThread().getName());
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
//        }
    }
}
