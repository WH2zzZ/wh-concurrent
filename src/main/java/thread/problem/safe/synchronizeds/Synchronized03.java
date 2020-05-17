package thread.problem.safe.synchronizeds;

/**
 * Synchronized修饰静态方法
 * @Author WangHan
 * @Create 5:00 下午 2019/12/1
 */
public class Synchronized03 {

    private static int value;

    /**
     * synchronized 修饰静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁
     * 由于静态成员不专属于任何一个实例对象，是类成员，因此通过class对象锁可以控制静态 成员的并发操作。
     * 需要注意的是如果一个线程A调用一个实例对象的非static synchronized方法，而线程B需要调用这个实例对象所属类的静态 synchronized方法，是允许的，不会发生互斥现象，
     * 因为访问静态 synchronized 方法占用的锁是当前类的class对象，而访问非静态 synchronized 方法占用的锁是当前实例对象锁
     * @return
     */
    public synchronized static int getNext(){
        return value++;
    }

    public static void main(String[] args) {
        makeThread();

        makeThread();
    }
    /**
     * 创建一个线程
     */
    private static void makeThread() {
        Thread thread1 = new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " " + Synchronized03.getNext());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.setDaemon(false);
        thread1.start();
    }
}
