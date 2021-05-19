package thread.problem.threadlocal;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;

/**
 * @Author WangHan
 * @Create 2020/5/25 1:32 上午
 */
@Slf4j
public class Demo02 {

    public static void main(String[] args) {

        WeakReference<ThreadLocal<String>> threadLocal = new WeakReference<>(new ThreadLocal<>());


        threadLocal.get().set("10");
        System.out.println(threadLocal.get());
        System.gc();
        System.out.println(threadLocal.get());
    }
}

