package thread.problem.safe.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock
 *
 * 条件变量
 * @Author WangHan
 * @Create 2019/12/7 1:20 下午
 */
@Slf4j
public class Lock06 {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        //创建一个新的条件变量
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        lock.lock();
        //进入condition1等待
        try {
            condition1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //唤醒condition1里面的一个线程
        condition1.signal();
    }
}
