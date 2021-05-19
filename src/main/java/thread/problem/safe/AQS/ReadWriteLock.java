package thread.problem.safe.AQS;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author WangHan
 * @Create 2020/5/24 11:45 上午
 */
@Slf4j
public class ReadWriteLock {

    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
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
            dataContainer.write(100);
        }).start();
    }

}

@Slf4j
@Data
class DataContainer{

    private Object data;
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
    private ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    public Object read(){
        readLock.lock();
        log.info("获取读锁:{}", Thread.currentThread().getName());
        try {
            log.info("读取数据:{}", Thread.currentThread().getName());
            Thread.sleep(2000);
            return data;
        } catch (InterruptedException e) {
            return null;
        } finally {
            readLock.unlock();
            log.info("读锁解锁:{}", Thread.currentThread().getName());
        }
    }

    public void write(Object data){
        log.info("准备获取写锁:{}", Thread.currentThread().getName());
        writeLock.lock();
        log.info("获取写锁:{}", Thread.currentThread().getName());
        try {
            this.data = data;
            log.info("写入数据:{}", Thread.currentThread().getName());
        }finally {
            writeLock.unlock();
            log.info("解锁写锁:{}", Thread.currentThread().getName());
        }
    }

}
