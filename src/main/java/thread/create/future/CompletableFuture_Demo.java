package thread.create.future;

import org.junit.jupiter.api.Test;

import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @Author WangHan
 * @Create 2021/5/16 1:19 上午
 */
public class CompletableFuture_Demo {

    /**
     * Future弊端：
     *
     * Future不能主动设置计算结果值，一旦调用get()进行阻塞等待，要么当计算结果产生，要么超时，才会返回。
     *
     * CompletableFuture能够主动设置计算的结果值（主动终结计算过程，即completable），从而在某些场景下主动结束阻塞等待。
     */
    @Test
    public void whyUseCompletableFuture() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            try{
                Thread.sleep(1000L);
                return "test";
            } catch (Exception e){
                return "failed test";
            }
        });
        future.complete("manual test");
        //这里会发现使用的是complete里面的值：manual test
        System.out.println(future.join());
    }

    /**
     * 简述：
     *  supplyAsync()也可以用来创建CompletableFuture实例。
     *  通过该函数创建的CompletableFuture实例会异步执行当前传入的计算任务。
     *  在调用端，则可以通过get或join获取最终计算结果。
     *
     * 方法详解：
     *  第一种只需传入一个Supplier实例（一般使用lambda表达式），此时框架会默认使用ForkJoin的线程池来执行被提交的任务。
     *  第二种可以指定自定义的线程池，然后将任务提交给该线程池执行。
     *
     * 注意：runAsync适合创建不需要返回值的计算任务，其他同supplyAsync()
     */
    @Test
    public void testSupplyAsync(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printInfo("1号位结束运行");
            return "1";
        });

        System.out.println("CompletableFuture 运行结束" + future.join());
    }

    @Test
    public void testThenApply(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printInfo("1号位运行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printInfo("1号位结束");
            return "1";
        }).thenApplyAsync(param -> {
            printInfo("2号位结束运行");
            return "2";
        });

        System.out.println("CompletableFuture 运行结束" + future.join());
    }

    /**
     *
     */
    @Test
    public void testThenCompose(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printInfo("1号位运行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printInfo("1号位结束");
            return "1";
        }).thenCompose(param -> CompletableFuture.supplyAsync(() -> {
            printInfo("2号位结束运行");
            return "2";
        }));

        System.out.println("CompletableFuture 运行结束" + future.join());
    }

    /**
     * thenCombine
     * 允许前后连接的两个任务可以并行执行（后置任务不需要等待前置任务执行完成），
     * 最后当两个任务均完成时，再将其结果同时传递给下游处理任务，从而得到最终结果
     *
     * 由于默认会会使用ForkJoin线程池，由于第一个任务执行过快，所以会出现同一个线程id的情况
     *
     * thenCombineAsync()
     * 区别在于，最后合并的任务会交给线程池去解决
     */
    @Test
    public void testCombineAsync(){
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            printInfo("1号位结束运行");
            return "1";
        }).thenCombineAsync(CompletableFuture.supplyAsync(() -> {
//        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            printInfo("2号位结束运行");
            return "2";
        }), (result1, result2) -> {
            printInfo("Combine号位结束运行");
            return result1 + result2;
        });

        System.out.println("CompletableFuture 运行结束，结果：" + future.join());
    }

    private void printInfo(String info) {
        info = new StringJoiner("|")
                .add(Thread.currentThread().getId() + "")
                .add(info)
                .toString();
        System.out.println(info);
    }
}
