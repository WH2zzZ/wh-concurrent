package thread.problem.safe.atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子类型操作
 * 解决aba问题
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic04_StampedReference {

    public static void main(String[] args) {
        AtomicStampedReference<Integer> i = new AtomicStampedReference(10, 0);

        //获取值
        System.out.println(i.getReference());
        //获取版本号
        System.out.println(i.getStamp());
        //修改
        int stamp = i.getStamp();
        //此处解决ABA，通过一个版本号来解决
        i.compareAndSet(10, 20, stamp, ++stamp);
        System.out.println(i.getStamp());
        System.out.println(i.getReference());

    }
}
