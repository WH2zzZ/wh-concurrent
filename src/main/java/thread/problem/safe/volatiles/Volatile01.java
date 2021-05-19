package thread.problem.safe.volatiles;

import lombok.extern.slf4j.Slf4j;

/**
 * 可见性
 *
 * @Author WangHan
 * @Create 2019/12/2 9:11 下午
 */
@Slf4j
public class Volatile01 {

    volatile static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (flag) {
                //不可有任何代码
            }
            log.info("Thread:{} end!!!!，flag:{}", Thread.currentThread().getName(), flag);
        });

        thread.start();
        Thread.sleep(1000);
        log.info("主线程准备停止，flag:{}", flag);
        flag = false;
    }
}