package day02_thread_problem.live;


public class Target implements Runnable {


    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + "  run ...");
            
        }
    }
}
