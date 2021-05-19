package thread.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import thread.problem.type.ReentryLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 交叉打印
 * @Author WangHan
 * @Create 2020/5/17 8:46 下午
 */
@Slf4j
public class Test01 {

    public static void main(String[] args) throws InterruptedException {
//        Notify01 notify = new Notify01(1, 10);
//        new Thread(() -> notify.print("线程1执行", 1, 2), "线程1").start();
//        new Thread(() -> notify.print("线程2执行", 2, 3), "线程2").start();
//        new Thread(() -> notify.print("线程3执行", 3, 1), "线程3").start();
        Notify02 notify = new Notify02(10);
        Condition condition1 = notify.newCondition();
        Condition condition2 = notify.newCondition();
        Condition condition3 = notify.newCondition();
        new Thread(() -> notify.print("线程1执行", condition1, condition2), "线程1").start();
        new Thread(() -> notify.print("线程2执行", condition2, condition3), "线程2").start();
        new Thread(() -> notify.print("线程3执行", condition3, condition1), "线程3").start();
        Thread.sleep(1000);

        notify.lock();
        try {
            condition1.signal();
        }finally {
            log.info("======start======");
            notify.unlock();
        }

    }

}


/**
 * sync实现
 */
@Slf4j
@Data
@AllArgsConstructor
class Notify01 {

    private int flag;

    private int loopCount;

    public void print(String content, int waitFlag, int nextFlag){

        for (int i = 0; i < loopCount; i++) {

            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        log.info("等待中，被打断：{}", Thread.currentThread().getName());
                    }
                }
                log.info("thread:{},content:{},当前循环次数:{}", Thread.currentThread().getName(), content, i);
                flag = nextFlag;
                this.notifyAll();
            }

        }
    }
}

/**
 * lock实现
 */
@Slf4j
@Data
@AllArgsConstructor
class Notify02 extends ReentrantLock {

    private int loopCount;

    public void print(String content, Condition currentCondition, Condition nextCondition){
        for (int i = 0; i < loopCount; i++) {
            this.lock();
            try {
                currentCondition.await();
                log.info("thread:{},content:{},当前循环次数:{}", Thread.currentThread().getName(), content, i);
                nextCondition.signalAll();
            } catch (InterruptedException e) {
                log.info("等待被打断");
            } finally {
                this.unlock();
            }
        }
    }
}
