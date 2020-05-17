package thread.problem.type;

/**
 * 重入锁
 *
 * @Author WangHan
 * @Create 2019/12/1 11:39 下午
 */
public class ReentryLock {

    public synchronized void a(){
        System.out.println("a");
        b();
    }

    public synchronized void b() {
        System.out.println("b");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ReentryLock reentryLock = new ReentryLock();
        new Thread(() -> reentryLock.a()).start();

        //上面的线程通过synchronized的重入锁的性质进入b后，睡10s钟
        //这个时候下面线程调用线程b，同样会等待b的同步锁的释放
        new Thread(() -> reentryLock.b()).start();
    }
}
