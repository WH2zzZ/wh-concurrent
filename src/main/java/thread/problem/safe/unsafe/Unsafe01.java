package thread.problem.safe.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author WangHan
 * @Create 2020/5/20 12:48 上午
 */
public class Unsafe01 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(Unsafe.class);
        System.out.println(unsafe);
    }
}
