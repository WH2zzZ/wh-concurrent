package thread.problem.safe.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lock
 *
 * 可重入
 * @Author WangHan
 * @Create 2019/12/7 1:20 下午
 */
@Slf4j
public class Lock02 {

    private int value = 0;
    /**
     * 所有的线程都要拿一把锁，不能放到next方法里面，这样所有线程都是不同的锁
     */
    ReentrantLock lock = new ReentrantLock();

    public int next(){
        lock.lock();
        try {
            log.info("准备重入");
            printLog();
            return value++;
        }finally {
            lock.unlock();
        }
    }

    private void printLog() {
        lock.lock();
        try {
            log.info("重入了");

        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Lock02 lock02 = new Lock02();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + " : " + lock02.next())).start();
    }
}
