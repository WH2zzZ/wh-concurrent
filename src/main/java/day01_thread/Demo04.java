package day01_thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Demo04 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("call....wait.....");
        Thread.sleep(3000);
        return 1;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Demo04 demo04 = new Demo04();

        FutureTask<Integer> task = new FutureTask<>(demo04);

        Thread t = new Thread(task);

        t.start();

        //这串代码会先行
//        System.out.println("主线程run...run...run...");

        Integer result = task.get();
        System.out.println(result);
    }
}
