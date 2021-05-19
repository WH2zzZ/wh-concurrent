package thread.problem.safe.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author WangHan
 * @Create 2020/5/20 12:48 上午
 */
public class MyAtomicInteger {

    private volatile int value;
    private static final Long valueOffset;
    private static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeField.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafeField.get(Unsafe.class);
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public int getValue(){
        return value;
    }

    public void decrement(int num){
        while (true){
            int preValue = getValue();
            int nextValue = preValue - num;
            if (UNSAFE.compareAndSwapObject(this, valueOffset, preValue, nextValue)){
                break;
            }
        }
    }
}