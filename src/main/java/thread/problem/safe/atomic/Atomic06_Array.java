package thread.problem.safe.atomic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 数组类型的原子类，可以针对数组中的每个元素进行原子性的操作
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic06_Array {

    public static void main(String[] args) {

        demo(
            () -> new int[10],
            array -> array.length,
            (array, index) -> array[index]++,
            array -> System.out.println(Arrays.toString(array))
        );

        demo(
                () -> new AtomicIntegerArray(10),
                AtomicIntegerArray::length,
                AtomicIntegerArray::getAndIncrement,
                System.out::println
        );
    }

    private static <T> void demo(Supplier<T> arraySupplier,
                                 Function<T, Integer> lengthFunction,
                                 BiConsumer<T, Integer> putBiConsumer,
                                 Consumer<T> printConsumer){

        ArrayList<Thread> threads = new ArrayList<>();

        T array = arraySupplier.get();

        Integer length = lengthFunction.apply(array);

        for (int i = 0; i < length; i++) {
            threads.add(new Thread(() -> {
                //每个元素都++100次
                for (int j = 0; j < 1000; j++) {
                    putBiConsumer.accept(array, j % length);
                }
            }));
        }

        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        printConsumer.accept(array);


    }
}
