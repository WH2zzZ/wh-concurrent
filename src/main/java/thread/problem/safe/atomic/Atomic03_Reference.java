package thread.problem.safe.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子类型操作
 *
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic03_Reference {

    public static void main(String[] args) {
        AtomicReference<Integer> i = new AtomicReference(10);

        //使用不方便 使用其他类型替换
        i.compareAndSet(0, 100);

        System.out.println(i);

        //乘法
        System.out.println(i.updateAndGet(operand -> operand * 5));

    }
}
