package thread.problem.safe.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock
 *
 * 锁超时
 * @Author WangHan
 * @Create 2019/12/7 1:20 下午
 */
@Slf4j
public class Lock04 {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.info("尝试获取锁");
            try {
                if (!lock.tryLock(1, TimeUnit.SECONDS)){
                    log.info("获取不到锁");
                    return;
                }
            } catch (InterruptedException e) {
                log.info("被人打断了，尝试获取锁失败");
            }

            try {
                log.info("获得锁");
            }finally {
                lock.unlock();
            }
        });

        lock.lock();
        try {
            t1.start();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
