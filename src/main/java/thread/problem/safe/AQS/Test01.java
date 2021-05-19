package thread.problem.safe.AQS;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AQS类详解
 * 为实现依赖于先进先出（FIFO）等待队列的阻塞锁和相关同步器（信号量，事件，等等）提供一个框架
 *
 * @Author WangHan
 * @Create 2019/12/8 1:23 下午
 */
public class Test01 {

    AbstractQueuedSynchronizer abstractQueuedSynchronizer;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        //进入ReentrantLock的lock方法查看具体实现
        lock.lock();
        lock.lockInterruptibly();
        Condition condition = lock.newCondition();
        condition.await();
        condition.signal();
        lock.unlock();
    }

}
