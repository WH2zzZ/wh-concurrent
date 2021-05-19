package thread.create.future;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * 改进future的get方法是阻塞性的
 * @Author WangHan
 * @Create 2021/5/20 12:42 上午
 */
public class ListenableFuture_Demo {


    @Test
    public void test01(){
        ListeningExecutorService service = MoreExecutors
                .listeningDecorator(Executors.newFixedThreadPool(2));

        ListenableFuture<String> listenableFuture = service.submit(new Callable<String>() {
            @Override
            public String call() throws InterruptedException {
                Thread.sleep(5000);
                return "8";
            }
        });

        Futures.addCallback(listenableFuture, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String s) {
                System.out.println("success:" + s);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("出现异常啦" + throwable.getMessage());
            }
        }, service);

        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("i" + i);
        }

    }
}
