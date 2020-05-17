package thread.problem.safe.atomic;

import thread.util.User;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子类型操作
 *
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic01 {

    /**
     * 原子更新基本类型
     */
    private AtomicInteger atomicValue = new AtomicInteger(0);
    private int value = 0;

    private int[] array = {2,1,3,4};
    /**
     * 原子更新数组
     */
    private AtomicIntegerArray atomicArray = new AtomicIntegerArray(array);

    private AtomicIntegerFieldUpdater<User> atomicAge = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");

    /**
     * 原子更新抽象类型
     * 是对user的原子操作，并不会对user内部的属性进行原子操作
     */
    private AtomicReference<User> atomicUser = new AtomicReference<>();

    public int getNext(){
        int value = atomicValue.getAndIncrement();
        atomicUser.getAndSet(new User());
        atomicArray.getAndIncrement(1);

        User user = new User();
        user.setAge(value);
        atomicAge.getAndIncrement(user);
        return value;
//        return value.getAndAdd(10);
    }

    public static void main(String[] args) {

        Atomic01 atomic01 = new Atomic01();

        new Thread(() -> {
            while (true){
                System.out.println(atomic01.getNext());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true){
                System.out.println(atomic01.getNext());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
