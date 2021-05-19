package thread.problem.safe.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子类型操作
 *
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic03_Integer {

    public static void main(String[] args) {
        AtomicInteger i = new AtomicInteger(0);

        //使用不方便 使用其他类型替换
        i.compareAndSet(0, 100);

        //++i
        System.out.println(i.incrementAndGet());
        //i++
        System.out.println(i.getAndIncrement());
        System.out.println(i.get());

        //--i
        System.out.println(i.decrementAndGet());
        //i--
        System.out.println(i.getAndDecrement());
        System.out.println(i);

        System.out.println(i.addAndGet(100));
        System.out.println(i.getAndAdd(100));
        System.out.println(i);

        //乘法
        System.out.println(i.updateAndGet(operand -> operand * 5));

    }
}
