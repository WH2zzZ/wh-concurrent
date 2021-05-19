package thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author WangHan
 * @Create 2020/5/21 11:39 下午
 */
@Slf4j
public class Demo {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(1000);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();


    }
}
