package thread.problem.live;


/**
 * 高优先级吞噬所有低优先级的CPU时间片
 *
 * 如何避免:
 *    使用琐代替synchronized, 来保证线程不会被永久的堵塞在一个等待进入的同步块
 */
public class Hunger02 {

    public static void main(String[] args) {

        Target target = new Target();

        Thread thread1 = new Thread(target);
        Thread thread2 = new Thread(target);

        //由于任务中的run方法上了对象锁，而两个线程跑的都是同一个对象的run方法，所以线程1会被堵塞
//        thread1.setDaemon(true);
        thread1.start();
//        thread2.start();
    }

    public static class Target implements Runnable {
        /**
         * run方法上锁
         */
        @Override
        public synchronized void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "  run ...");
            }
        }
    }
}
