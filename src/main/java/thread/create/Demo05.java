package thread.create;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器创建线程
 */
public class Demo05 {

    public static void main(String[] args) {

        Timer timer = new Timer();

        //每隔1s执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task is run...");
            }
        }, 0, 1000);
    }
}
