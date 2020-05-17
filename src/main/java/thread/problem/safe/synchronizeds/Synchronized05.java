package thread.problem.safe.synchronizeds;

/**
 * Synchronized的可见性
 *
 * 多个线程拿到的必须是同一个锁
 * @Author WangHan
 * @Create 5:00 下午 2019/12/1
 */
public class Synchronized05 {

    private int a = 1;

    public int getA(){
        return a;
    }

    public synchronized int getALock(){
        return a;
    }

    public void setA(int a){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.a = a;
    }

    public synchronized void setALock(int a){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.a = a;
    }

    public static void main(String[] args) {

        Synchronized05 synchronized05 = new Synchronized05();

        //这种方式并没有保证对A的操作对其他线程的可见性
        new Thread(() -> synchronized05.setA(10)).start();
        new Thread(() -> System.out.println(synchronized05.getA())).start();

        //通过加锁保证一个线程的变动对其他线程的可见性
        new Thread(() -> synchronized05.setALock(20)).start();
        new Thread(() -> System.out.println(synchronized05.getALock())).start();

    }

}
