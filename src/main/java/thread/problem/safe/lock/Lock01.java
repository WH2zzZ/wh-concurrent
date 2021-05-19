package thread.problem.safe.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lock
 * 需要显示获取和释放锁，繁琐能让代码更灵活
 * 可以实现公平性
 *
 * 非阻塞的获取锁
 * 能被中断的获取锁
 * 超时获取锁
 *
 * @Author WangHan
 * @Create 2019/12/7 1:20 下午
 */
public class Lock01 {

    private int value = 0;
    /**
     * 所有的线程都要拿一把锁，不能放到next方法里面，这样所有线程都是不同的锁
     */
    ReentrantLock lock = new ReentrantLock();

    public int next(){
        lock.lock();
        try {
            return value++;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Lock01 lock01 = new Lock01();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + " : " + lock01.next())).start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + " : " + lock01.next())).start();
        new Thread(() -> System.out.println(Thread.currentThread().getName() + " : " + lock01.next())).start();
    }
}
