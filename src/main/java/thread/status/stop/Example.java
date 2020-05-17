package thread.status.stop;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;
import thread.create.Demo01;

import java.util.Date;

/**
 *
 */
@Slf4j
public class Example extends Thread {

    public Example(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            log.info("开始执行：{}", new Date());
        }
        // 我要休息10秒钟，亲，不要打扰我哦
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("线程中断");
        }
        log.info("结束执行：{}", new Date());
    }

    public static void main(String[] args) {
        Example d1 = new Example("first-thread");

        d1.start();
        // 4秒后中断
        try {
            Thread.sleep(4000);
//            d1.stop(); //会强制中止线程,即使还有未完成的任务,这可能导致资源未施放等
            d1.interrupt(); //会中止线程,但是会让线程把当前还没完成的任务做完,这就很可能导致并没有真正的关闭线程,需要线程里面加入一个判断标识,来时刻判断是否真的线程被中断掉了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
