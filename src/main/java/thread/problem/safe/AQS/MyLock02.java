package thread.problem.safe.AQS;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Slf4j
class Test{
    public static void main(String[] args) throws InterruptedException {
        MyLock02 myLock02 = new MyLock02();

        Thread t1 = new Thread(() -> {

            log.info("准备加锁：{}", Thread.currentThread().getName());
            try {
                myLock02.lock();
//                myLock02.lockInterruptibly();
            } catch (Exception e) {
                log.info("被打断了：{}", Thread.currentThread().getName());
            }
            try {
                log.info("加锁成功：{}", Thread.currentThread().getName());
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
            } finally {
                log.info("解锁成功：{}", Thread.currentThread().getName());
                myLock02.unlock();
            }
        }, "t1");
        t1.start();
        Thread.sleep(100);

        new Thread(() -> {

            log.info("准备加锁：{}", Thread.currentThread().getName());
            myLock02.lock();
            try {
                log.info("加锁成功：{}", Thread.currentThread().getName());
            } finally {
                log.info("解锁成功：{}", Thread.currentThread().getName());
                myLock02.unlock();
            }
        }, "t2").start();

        new Thread(() -> {

            log.info("准备加锁：{}", Thread.currentThread().getName());
            //只要加锁失败就打断
            while (!myLock02.tryLock()){
                log.info("加锁失败，打断：{}", Thread.currentThread().getName());
                //打断t1的锁
                t1.interrupt();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                log.info("加锁成功：{}", Thread.currentThread().getName());
            } finally {
                log.info("解锁成功：{}", Thread.currentThread().getName());
                myLock02.unlock();
            }
        }, "t3").start();
    }
}


/**
 * 手动实现锁
 *
 * @Author WangHan
 * @Create 2019/12/7 4:30 下午
 */
public class MyLock02 implements Lock{

    private Helper helper = new Helper();


    /**
     * 加锁，不成功进入等待队列等待
     */
    @Override
    public void lock() {
        helper.acquire(1);
    }

    /**
     * 加锁，可打断
     * @throws InterruptedException
     */
    @Override
    public void lockInterruptibly() throws InterruptedException {
        helper.acquireInterruptibly(1);
    }

    /**
     * 尝试加锁，带超时时间
     * @return
     */
    @Override
    public boolean tryLock() {
        return helper.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return helper.tryAcquireNanos(1, unit.toNanos(time));
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        helper.release(1);
    }

    /**
     * 创建同步变量
     * @return
     */
    @Override
    public Condition newCondition() {
        return helper.instanceCondition();
    }

    /**
     * 非公共内部帮助器类
     * @Author WangHan
     * @Create 11:14 下午 2019/12/9
     */
    private class Helper extends AbstractQueuedSynchronizer{

        /**
         * 尝试获取锁
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
            int state = getState();
            Thread thread = Thread.currentThread();
            //如果第一个线程进来，可以获取锁
            if (state == 0){
                if (compareAndSetState(0, arg)){
                    setExclusiveOwnerThread(thread);
                    return true;
                }
            }else if (getExclusiveOwnerThread() == thread){
                //支持重入锁
                setState(state + 1);
                return true;
            }
            //第二个线程进来，拿不到锁

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //锁的获取和释放是同一把锁
            if (!Thread.currentThread().equals(getExclusiveOwnerThread())){
                throw new RuntimeException();
            }

            int state = getState() - arg;

            boolean flag = false;
            if (state == 0){
                setExclusiveOwnerThread(null);
                flag = true;
            }

            setState(state);

            return flag;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        Condition instanceCondition(){
            return new ConditionObject();
        }


    }
}
