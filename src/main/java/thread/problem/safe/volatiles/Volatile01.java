package thread.problem.safe.volatiles;

/**
 * 轻量级锁
 *
 * @Author WangHan
 * @Create 2019/12/2 9:11 下午
 */
public class Volatile01 {

    public static class MyThread extends Thread{
        private volatile boolean flag = true;

        public void stopThread(){
            flag = false;
        }

        @Override
        public void run() {
            while (flag){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " run");
            }
        }
    }
    public static void main(String[] args) {
        MyThread myThread = new MyThread();

        myThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        myThread.stopThread();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
