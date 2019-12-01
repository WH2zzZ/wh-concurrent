package thread.status.stop;


/**
 * 中断线程最好的，最受推荐的方式是，使用共享变量（shared variable）发出信号，告诉线程必须停止正在运行的任务。
 * 线程必须周期性的核查这一变量（尤其在冗余操作期间），然后有秩序地中止任务
 */
class Example2 extends Thread {
    volatile boolean stop = false;

    public static void main(String args[]) throws Exception {
        Example2 thread = new Example2();

        System.out.println("线程开启");
        thread.start();
        Thread.sleep(3000);

        System.out.println("中断线程");
        thread.stop = true;

        //给线程退出提供足够的时间
        Thread.sleep(3000);
        System.out.println("应用结束");
        //System.exit( 0 );
    }

    @Override
    public void run() {
        //周期性的核查,此处也可以使用线程的isInterrupted方法
        while (!stop) {
            System.out.println("Thread is running...");
            long time = System.currentTimeMillis();
            while ((System.currentTimeMillis() - time < 1000) && (!stop)) {
            }
        }
        System.out.println("线程退出");
    }
}