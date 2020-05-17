package thread.problem.safe.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 手动实现锁
 *
 * @Author WangHan
 * @Create 2019/12/7 4:30 下午
 */
public class MyLock implements Lock {

    private boolean isLock = false;

    Thread lockBy = null;

    private int lockCount = 0;

    @Override
    public synchronized void lock() {

        Thread currentThread = Thread.currentThread();

        //判断是否已经被获取锁
        while (isLock && currentThread.equals(lockBy)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isLock = true;
        lockBy = currentThread;
        lockCount++;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public synchronized void unlock() {
        if (Thread.currentThread().equals(lockBy)){
            lockCount--;
            if (lockCount == 0){
                isLock = false;
                notify();
            }
        }
        isLock = false;
        notify();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
