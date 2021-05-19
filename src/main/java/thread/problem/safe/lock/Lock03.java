package thread.problem.safe.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lock
 *
 * 可打断
 * @Author WangHan
 * @Create 2019/12/7 1:20 下午
 */
@Slf4j
public class Lock03 {

    /**
     * 所有的线程都要拿一把锁，不能放到next方法里面，这样所有线程都是不同的锁
     */

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                //如果没有竞争，这个方法就可以获取到lock对象锁
                //如果有竞争，那么就会进入阻塞队列，可以被其他线程用interupt打断
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                log.info("未获取到锁，被打断");
                return;
            }
            try {
                log.info("获取到锁");
            } finally {
                lock.unlock();
            }
        });

        lock.lock();
        try {
            t1.start();
            Thread.sleep(1000);
            log.info("打断t1");
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
