package thread.pool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Timer来实现定时功能，但是由于所有任务都是同一个线程来调度的，因此所有的任务都是串行执行的，
 * 同一个时间只能有一个任务执行，前一个任务的延迟或异常都将会影响到之后的任务
 *
 * @Author WangHan
 * @Create 2020/5/23 12:56 下午
 */
@Slf4j
public class ScheduleThreadPoolDemo {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);

        scheduledThreadPool.schedule(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("任务1执行了");
        }, 1, TimeUnit.SECONDS);

        scheduledThreadPool.schedule(() -> {
            try {
                log.info("任务2执行了");
                int i = 1 / 0 ;
            } catch (Exception e) {
                e.printStackTrace();
                log.info("任务2运行异常");
            }
        }, 1, TimeUnit.SECONDS);

        //从上个任务开始的时间算
        scheduledThreadPool.scheduleAtFixedRate(() -> {
            log.info("任务3每隔一秒循环执行了");
        }, 1, 1, TimeUnit.SECONDS);
        //从上个任务结束的时间算
        scheduledThreadPool.scheduleWithFixedDelay(() -> {
            log.info("任务4每隔一秒循环执行了");
        }, 1, 1, TimeUnit.SECONDS);
    }
}
