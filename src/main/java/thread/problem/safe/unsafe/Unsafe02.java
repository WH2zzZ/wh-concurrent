package thread.problem.safe.unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Author WangHan
 * @Create 2020/5/20 12:48 上午
 */
public class Unsafe02 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(Unsafe.class);
        System.out.println(unsafe);

        //获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("zhang_san");

        System.out.println(teacher);
        unsafe.compareAndSwapInt(teacher, idOffset, 1, 2);
        System.out.println(teacher);
        unsafe.compareAndSwapObject(teacher, nameOffset, "zhang_san", "wang_han");
        System.out.println(teacher);
    }
}

@Data
class Teacher{
    volatile int id;
    volatile String name;
}
