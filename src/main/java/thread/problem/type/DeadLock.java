package thread.problem.type;

/**
 * 死锁
 *
 * @Author WangHan
 * @Create 2019/12/2 12:02 上午
 */
public class DeadLock {

    private Object object01 = new Object();
    private Object object02 = new Object();

    public void a(){
        synchronized (object01){
            System.out.println(Thread.currentThread().getName() + " : object01 lock");
            synchronized (object02){
                System.out.println(Thread.currentThread().getName() + " : object02 lock");
            }
        }
    }

    public void b(){
        synchronized (object02){
            System.out.println(Thread.currentThread().getName() + " : object02 lock");
            synchronized (object01){
                System.out.println(Thread.currentThread().getName() + " : object01 lock");
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        //互相锁住了对方想要获取的锁对象
        DeadLock deadLock = new DeadLock();
        new Thread(() -> deadLock.a()).start();

        deadLock.b();
    }
}
