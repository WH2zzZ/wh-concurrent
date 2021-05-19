package thread.problem.safe.AQS;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * @Author WangHan
 * @Create 2020/5/24 11:45 上午
 */
@Slf4j
public class StampedLockDemo {

    public static void main(String[] args) {
        DataContainerStamped dataContainer = new DataContainerStamped();
        dataContainer.write(100);
        //读-读不冲突
        new Thread(() -> {
            Object data = dataContainer.read();
            log.info("data:{}", data);
        }).start();

        new Thread(() -> {
            Object data = dataContainer.read();
            log.info("data:{}", data);
        }).start();

        //读-写冲突
        new Thread(() -> {
            dataContainer.write(200);
        }).start();
    }

}

@Slf4j
@Data
class DataContainerStamped{

    private Object data;
    private StampedLock stampedLock = new StampedLock();

    public Object read(){
        //获取当前戳
        long stamped = stampedLock.tryOptimisticRead();
        log.info("获取读锁:{}", Thread.currentThread().getName());
        try {
            log.info("校验stamped:{} stamped:{}", Thread.currentThread().getName(), stamped);
            Thread.sleep(2000);

            //先读取到结果
            Object result = data;
            //校验戳是否有变化，没变化直接返回
            if (stampedLock.validate(stamped)){
                log.info("stamped通过:{} stamped:{}", Thread.currentThread().getName(), stamped);
                return result;
            }
            //发现戳变化了升级锁为读锁
            //然后再读一次
            stamped = stampedLock.readLock();
            log.info("stamped不通过:{} stamped:{}", Thread.currentThread().getName(), stamped);
            return data;
        } catch (InterruptedException e) {
            return null;
        } finally {
            stampedLock.unlockRead(stamped);
            log.info("读锁解锁:{}", Thread.currentThread().getName());
        }
    }

    public void write(Object data){
        log.info("准备获取写锁:{}", Thread.currentThread().getName());
        long stamped = stampedLock.writeLock();
        log.info("获取写锁:{}", Thread.currentThread().getName());
        try {
            this.data = data;
            log.info("写入数据:{}", Thread.currentThread().getName());
        }finally {
            stampedLock.unlock(stamped);
            log.info("解锁写锁:{}", Thread.currentThread().getName());
        }
    }

}
