package thread.partten;

import lombok.extern.slf4j.Slf4j;

/**
 * 如果现在不合适执行这个操作，或者没必要执行这个操作，就停止处理，直接返回。
 * 在Balking模式中，如果守护条件不成立，就立即中断处理。
 * @Author WangHan
 * @Create 2020/5/18 1:22 上午
 */
@Slf4j
public class Balking {

    public static void main(String[] args) {

        TwoPhaseEnd twoPhaseEnd = new TwoPhaseEnd();
        twoPhaseEnd.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        twoPhaseEnd.stop();
    }
}

@Slf4j
class TwoPhaseEnd {

    private Thread monitor;
    private volatile boolean stop = false;
    private volatile boolean balking = false;

    public void start(){
        //只能执行一次start,执行完一次start之后，如果没有stop掉， 就不可以再start了，、
        //通过balking表示当前是否已经有一个start了
        synchronized (this){
            if (balking){
                return;
            }
            balking = true;
        }

        monitor = new Thread(() -> {
            while (!stop){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.info("监控线程中断ing............");
                }
                log.info("开始监控线程。。。。Thread:{}", Thread.currentThread().getName());
            }
            balking = false;
            log.info("Thread:{} 终止", Thread.currentThread().getName());
        }, "监控线程");
        monitor.start();
    }

    public void stop(){
        if (monitor != null){
            this.stop = true;
            //立即打断sleep
            monitor.interrupt();
            return;
        }
        log.warn("未开启监控线程");
    }

}
