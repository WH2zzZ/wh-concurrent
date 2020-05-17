package thread.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Author WangHan
 * @Create 2020/5/10 3:22 下午
 */
@Slf4j
public class StartRunDemo {



    @Test
    public void testRun(){
        Thread thread = new Thread(() -> log.info("{} run...", Thread.currentThread()));
        //可以看出，run是main线程执行的，但是start是新的线程中执行的
        //也就是说，run不会开启一个线程，而start才会启动新的线程
        thread.run();
        thread.start();
    }
}
