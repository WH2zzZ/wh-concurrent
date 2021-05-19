package thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;

/**
 * 饥饿问题
 * @Author WangHan
 * @Create 2020/5/22 12:33 上午
 */
@Slf4j
public class HungerProblem {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BlockingQueue<Runnable> blockingQueue = new LinkedBlockingDeque<>();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2, 3, 1000,
                TimeUnit.SECONDS, blockingQueue);


        //总共就两个线程，现在一个两个线程都需要额外的一个线程去做菜，才能执行下去，但是又没有多余的线程，就会陷入一直等待
        pool.execute(() -> {
            log.info("开始点单");
            Future<String> future = pool.submit(() -> {
                log.info("做好了菜");
                return "宫保鸡丁";
            });
            try {
                log.info("上菜:{}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        pool.execute(() -> {
            log.info("开始点单");
            Future<String> future = pool.submit(() -> {
                log.info("做好了菜");
                return "辣椒肉丝";
            });
            try {
                log.info("上菜:{}", future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }
}
