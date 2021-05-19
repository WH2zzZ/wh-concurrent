package thread.problem.safe.atomic;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子类型操作
 * 解决aba问题
 * 有时候并不关心被更改了多少次，只关心是否被更改过
 * @Author WangHan
 * @Create 2019/12/3 11:28 下午
 */
public class Atomic07_Field {

    public static void main(String[] args) {
        Student student = new Student();
        student.setName("zhangsan");
        AtomicReferenceFieldUpdater<Student, String> nameUpdater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        System.out.println(student);
        nameUpdater.compareAndSet(student, "zhangsan", "wanghan");
        System.out.println(student);
    }
}

@Data
class Student{
    public volatile String name;
}
