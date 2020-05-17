package thread.problem.safe.AQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 手动实现锁
 *
 * @Author WangHan
 * @Create 2019/12/7 4:30 下午
 */
public class MyLock02 implements Lock{

    private Helper helper = new Helper();

    @Override
    public void lock() {
        helper.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        helper.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return helper.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return helper.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        helper.release(1);
    }

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

        Condition instanceCondition(){
            return new ConditionObject();
        }
    }
}
