package thread.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @Author WangHan
 * @Create 2020/5/10 3:54 下午
 */
@Slf4j
public class JoinDemo {

    private boolean flag = false;

    @Test
    public void testJoin(){
        Thread t1 = new Thread(() -> {
            log.info("{} run ..", Thread.currentThread());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = true;
            log.info("{} end ..", Thread.currentThread());
        }, "t1");
        t1.start();

        try {
            //等待t1线程执行完成，当前线程才会再执行
//            t1.join();
            t1.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("t1线程是否执行完毕 = {}", flag);
    }
}
