package thread.problem.safe.atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * 原子类型操作
 * 解决aba问题
 * 有时候并不关心被更改了多少次，只关心是否被更改过
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic05_MarkableReference {

    public static void main(String[] args) {
        AtomicMarkableReference<Integer> i = new AtomicMarkableReference(10, false);

        //获取值
        System.out.println(i.getReference());
        System.out.println(i.isMarked());
        i.compareAndSet(10, 20, false, true);
        System.out.println(i.getReference());
        System.out.println(i.isMarked());

    }
}
