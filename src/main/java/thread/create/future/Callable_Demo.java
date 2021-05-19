package thread.create.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author WangHan
 * @Create 2021/5/18 1:14 上午
 */
public class Callable_Demo {
    
    @Test
    public void testCallable(){
        Callable<String> callable = new Callable() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000);
                return "1111";
            }
        };

        FutureTask<String> stringFutureTask = new FutureTask<>(callable);
        Thread thread = new Thread(stringFutureTask);
        thread.start();

        try {
            System.out.println(stringFutureTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }
}
