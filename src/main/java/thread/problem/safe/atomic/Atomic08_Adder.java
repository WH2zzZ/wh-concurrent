package thread.problem.safe.atomic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 原子类型累加器操作
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic08_Adder {

    public static void main(String[] args) {
        adder(
                () -> new AtomicLong(0),
                AtomicLong::getAndIncrement
        );

        adder(
                LongAdder::new,
                LongAdder::increment
        );
    }

    private static <T> void adder(Supplier<T> supplier,
                                  Consumer<T> consumer){

        T adder = supplier.get();
        ArrayList<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 500000; j++) {
                    consumer.accept(adder);
                }
            }));
        }

        long startTime = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long endTime = System.nanoTime();
        System.out.println(adder + " cost:" + (endTime - startTime)/1000_000);


    }
}
