package thread.problem.safe.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟10位玩家游戏进入加载
 * @Author WangHan
 * @Create 2020/5/24 4:47 下午
 */
@Slf4j
public class CountdownLatchDemo02 {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] all = new String[10];

        CountDownLatch countDownLatch = new CountDownLatch(10);

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int j = 0; j < 10; j++) {
            int index = j;
            threadPool.submit(() -> {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(random.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    all[index] = i + "%";
                    System.out.print("\r" + Arrays.toString(all));
                }
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();
        System.out.println("\n start game");
        threadPool.shutdown();

    }
}
