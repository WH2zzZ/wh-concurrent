package thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;

/**
 * @Author WangHan
 * @Create 2020/5/22 12:33 上午
 */
@Slf4j
public class TestSubmit {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<>(5);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 10, 10,
                TimeUnit.SECONDS, blockingQueue, r -> {
                    Thread thread = new Thread(r);
                    log.info("创建线程:{}", thread.getName() + r);
                    return thread;
                });

        for (int i = 0; i < 2; i++) {
            int finalI = i;
            Future<Integer> future = threadPoolExecutor.submit(() -> {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return finalI;
            });
        }
    }
}
