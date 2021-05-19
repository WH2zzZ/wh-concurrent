package thread.pool;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @Author WangHan
 * @Create 2020/5/23 4:30 下午
 */
public class ForkJoinThreadPool {

    public static void main(String[] args) {
        ForkJoinPool threadPool = new ForkJoinPool(4);

        Integer invoke = threadPool.invoke(new MyTask(5));
        System.out.println(invoke);

        //任务拆分 MyTask(5) = MyTask(4) + 5
        //任务拆分 MyTask(4) = MyTask(3) + 4
        //任务拆分 MyTask(3) = MyTask(2) + 3
        //由于任务是相互依赖的，所以拆分度不高

    }
}

@Slf4j
@Data
class MyTask extends RecursiveTask<Integer>{

    private int n;

    public MyTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n == 1){
            log.info("join() {}", n);
            return 1;
        }

        MyTask task = new MyTask(n - 1);
        //让一个线程去执行
        task.fork();
        log.info("fork(){} + {}", n, task);
        //获取任务结果
        int result = n + task.join();
        log.info("join() = {} {}", n, task, result);
        return result;
    }
}
