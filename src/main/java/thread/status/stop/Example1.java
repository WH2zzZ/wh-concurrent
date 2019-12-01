package thread.status.stop;


/**
 * 有时候interrupt并不会真正的中断线程,它给了线程继续执行完的机会,如下例
 */
class Example1 extends Thread {
    boolean stop = false;

    public static void main(String args[]) throws Exception {
        Example1 thread = new Example1();

        System.out.println("线程开启");
        thread.start();

        //该处sleep为了让thread线程多运行一会儿,再中断
        Thread.sleep(3000);
        System.out.println("中断线程");

        thread.interrupt();

        //该处让thread线程中断一会儿,查看是否中断
        Thread.sleep(3000);
        System.out.println("应用结束");
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            while ((System.currentTimeMillis() - time < 1000)) {
            }
        }
        System.out.println("线程退出");
    }
}