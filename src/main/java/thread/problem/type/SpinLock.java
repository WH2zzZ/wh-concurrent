package thread.problem.type;

import java.util.Random;

/**
 * 自旋锁
 *
 * @Author WangHan
 * @Create 2019/12/1 11:45 下午
 */
public class SpinLock {

    public static void main(String[] args) {
        getThread().start();
        getThread().start();
        getThread().start();
        getThread().start();

        while (Thread.activeCount() != 1){
            //自旋，等待
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("还剩" + Thread.activeCount());
        }
        System.out.println("所有线程执行完毕");
    }

    private static Thread getThread() {
        return new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "run");

            try {
                Thread.sleep(new Random().nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "stop");
        });
    }
}
