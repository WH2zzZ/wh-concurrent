package thread.problem.live;


/**
 * 线程被永久堵塞在一个等待进入同步块的状态 等待的线程永远不被唤醒
 *
 * 如何避免:
 *      避免等待的线程不被唤醒这种情况
 */
public class Hunger03 {

    public static void main(String[] args) {

        Target target = new Target();

        Thread thread1 = new Thread(target);
        Thread thread2 = new Thread(target);

        //由于任务中的run方法上了对象锁，而两个线程跑的都是同一个对象的run方法，所以线程1会被堵塞
        thread1.start();
        thread2.start();

    }

    public static class Target implements Runnable {
        @Override
        public synchronized void run() {
            System.out.println(Thread.currentThread().getName() + "  run ...");
            System.out.println(Thread.currentThread().getName() + "  wait ...");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "  run ...");
            System.out.println(Thread.currentThread().getName() + "  stop ...");

        }
    }
}
