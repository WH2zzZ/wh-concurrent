package day01_thread_stop;


/**
 * 当线程等待某些事件发生而被阻塞，又会发生什么？当然，如果线程被阻塞，它便不能核查共享变量，也就不能停止。
 * 这在许多情况下会发生，例如调用Object.wait()、ServerSocket.accept()和DatagramSocket.receive()时，这里仅举出一些
 */
class Example3 extends Thread {
//    volatile boolean stop = false;

    public static void main(String args[]) throws Exception {
        Example3 thread = new Example3();

        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);


        System.out.println("中断线程");
        //如果线程阻塞，将不会检查此变量
//        thread.stop = true;
        //Thread.interrupt()被调用，线程便收到一个异常，于是逃离了阻塞状态并确定应该停止。
        //这种方法可以使用isInterrupted()方法来做这个中断标识,就不需要stop这个共享变量了
        thread.interrupt();

        Thread.sleep(3000);
        System.out.println("应用结束");
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
//        while (!stop) {
            System.out.println("Thread running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("线程已被中断");
            }
        }
        System.out.println("线程退出");
    }
}