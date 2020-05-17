package thread.create;

import java.util.concurrent.*;

/**
 * 实现callable 有返回值，和runnable也相似
 *
 */
public class Demo04 implements Callable<Integer> {

    /**
     * 相当于run方法
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("call....wait.....");
        Thread.sleep(3000);
        return 1;
    }

    public static void main(String[] args) {
        Demo04 demo04 = new Demo04();

        //创建一个线程任务
        FutureTask<Integer> task = new FutureTask<>(demo04);

        Thread t = new Thread(task);

        t.start();

        //这串代码会先行，说明开启线程执行也是需要时间的，但是主线程不会一直阻塞在那里
        System.out.println("主线程run...run...run...");

        //等待1s钟获取结果，如果结果还没有返回，报timeout异常
        try {
            //此处会阻塞
            Integer result = task.get();
//            Integer result = task.get(1, TimeUnit.SECONDS);
            System.out.println("等了一秒然后计算的结果" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //阻塞获取线程的数据，直到获取到数据为止
        try {
            Integer result = task.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
