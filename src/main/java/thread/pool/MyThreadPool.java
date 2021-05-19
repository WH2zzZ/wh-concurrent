package thread.pool;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 * @Author WangHan
 * @Create 2020/5/21 12:20 上午
 */
@Slf4j
class Test{
    public static void main(String[] args) {
//        MyThreadPool myThreadPool = new MyThreadPool(2, 100, TimeUnit.MILLISECONDS, 10);
        MyThreadPool myThreadPool = new MyThreadPool(
            2,
            100,
            TimeUnit.MILLISECONDS,
            10,
            (queue, task) -> {
                //拒绝策略
                //1.死等
                queue.put(task);
//                    boolean putResult = queue.put(task, 1000, TimeUnit.SECONDS);
            }
        );

        for (int i = 0; i < 20; i++) {
            int j = i;
            myThreadPool.execute(() -> {
                log.info("执行任务ing : {} Thread:{}", j, Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }
}

@Slf4j
@Data
public class MyThreadPool {

    private BlockingQueue<Runnable> taskQueue;

    private final HashSet<Worker> workes = new HashSet<>();

    private int coreSize;

    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy rejectPolicy;

    public MyThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity) {
        this.taskQueue = new BlockingQueue<>(queueCapacity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public MyThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCapacity, RejectPolicy rejectPolicy) {
        this(coreSize, timeout, timeUnit, queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        synchronized (workes){
            if (workes.size() < coreSize){
                Worker worker = new Worker(task);
                log.info("新建工作队列 worker:{}", worker);
                workes.add(worker);
                worker.start();
            }else {
//                taskQueue.put(task);
                taskQueue.tryPut(rejectPolicy, task);
                //可能会死等
                //带超时时间等待
                //抛弃任务执行
                //抛出异常
                //调用者自己执行任务

            }
        }
    }
    @FunctionalInterface
    interface RejectPolicy<T>{
        void reject(BlockingQueue<T> queue, T task);
    }

    @Data
    class Worker extends Thread{

        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task != null || (task = taskQueue.poll(timeout, timeUnit)) != null){
                try {
                    log.info("任务被执行 task:{}", task);
                    task.run();
                }finally {
                    task = null;
                }
            }

            synchronized (workes){
                log.info("工作完成 worker:{}", this);
                workes.remove(this);
            }
        }
    }
}

@Slf4j
@Data
class BlockingQueue<T> {
    /**
     * 任务队列
     */
    private Deque<T> queue = new ArrayDeque<>();

    private ReentrantLock lock = new ReentrantLock();

    /**
     * 生产者条件变量
     */
    private Condition producerCondition = lock.newCondition();

    /**
     * 消费者条件变量
     */
    private Condition consumerCondition = lock.newCondition();

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 容量
     */
    private int capacity;

    public T poll(long timeout, TimeUnit timeUnit){
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            while (queue.isEmpty()){
                log.info("阻塞队列为空，请等待");
                try {
                    //等待 到时候如果被打断了，返回值是剩余的等待时间
                    if (nanos <= 0){
                        return null;
                    }
                    nanos = consumerCondition.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T first = queue.removeFirst();
            producerCondition.signal();
            return first;
        }finally {
            lock.unlock();
        }
    }

    public T poll(){
        lock.lock();
        try {
            while (queue.isEmpty()){
                log.info("阻塞队列为空，请等待");
                try {
                    consumerCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T first = queue.removeFirst();
            producerCondition.signal();
            return first;
        }finally {
            lock.unlock();
        }
    }

    public void put(T element){
        lock.lock();
        try {
            log.info("进入阻塞队列，element:{}", element);
            while (queue.size() == capacity){
                log.info("阻塞队列满啦。。。。。。");
                producerCondition.await();
            }
            queue.addLast(element);
            consumerCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public boolean put(T element, long timeout, TimeUnit timeUnit){
        lock.lock();
        try {
            long nanos = timeUnit.toNanos(timeout);
            log.info("进入阻塞队列，element:{}", element);
            try {
                while (queue.size() == capacity){
                    log.info("阻塞队列满啦。。。。。。");
                    nanos = producerCondition.awaitNanos(nanos);
                    if (nanos <= 0){
                        log.info("添加失败，等待添加超时");
                        return false;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.addLast(element);
            consumerCondition.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    public void tryPut(MyThreadPool.RejectPolicy rejectPolicy, T element) {
        lock.lock();
        try {
            log.info("进入阻塞队列，element:{}", element);
            if (queue.size() == capacity){
                log.info("阻塞队列满啦。。。。。。");
                rejectPolicy.reject(this, element);
            }else {
                queue.addLast(element);
                consumerCondition.signal();
            }
        }finally {
            lock.unlock();
        }
    }

    public int size(){
        lock.lock();
        try {
            return queue.size();
        }finally {
            lock.unlock();
        }
    }
}