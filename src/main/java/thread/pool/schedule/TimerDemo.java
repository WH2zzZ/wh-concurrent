package thread.pool.schedule;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer来实现定时功能，但是由于所有任务都是同一个线程来调度的，因此所有的任务都是串行执行的，
 * 同一个时间只能有一个任务执行，前一个任务的延迟或异常都将会影响到之后的任务
 *
 * @Author WangHan
 * @Create 2020/5/23 12:56 下午
 */
@Slf4j
public class TimerDemo {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                log.info("任务1执行了");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                log.info("任务2执行了");
            }
        };

        timer.schedule(timerTask1, 1000);
        timer.schedule(timerTask2, 1000);
    }
}
