package thread.problem.live;


/**
 * 高优先级吞噬所有低优先级的CPU时间片
 *
 * 如何避免:
 *    设置合理的优先级
 */
public class Hunger01 {

    public static void main(String[] args) {

        Thread thread1 = new Thread(new Target());
        Thread thread2 = new Thread(new Target());
        Thread thread3 = new Thread(new Target());
        Thread thread4 = new Thread(new Target());


        //优先级在不同的平台上,表达的意思也是不同的
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread3.setPriority(Thread.MAX_PRIORITY);
        thread4.setPriority(Thread.MAX_PRIORITY);

        thread2.setPriority(Thread.MIN_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

    }

    public static class Target implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "  run ...");
            }
        }
    }
}
