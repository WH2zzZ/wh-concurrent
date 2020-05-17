package thread.partten;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 保护性暂停
 * @Author WangHan
 * @Create 2020/5/12 12:14 上午
 */
@Slf4j
public class GuardedSuspension {

    @Test
    public void test(){
        GuardedObject guardedObject = new GuardedObject();
        //线程1等待线程2的下载结果
        new Thread(() -> {
            Object response = guardedObject.get();
            log.info("{} 获取到结果 ：{}", Thread.currentThread().getName(), response);
        }).start();

        new Thread(() -> {
            int response = 2;
            log.info("{} 计算到结果 ：{}", Thread.currentThread().getName(), response);
            guardedObject.complete(response);
        }).start();
    }

    @Test
    public void testGuardedObjects() throws InterruptedException {
        //线程1等待线程2的下载结果
        new Thread(() -> {
            Integer id = GuardedObjects.createGuardedObject();
            log.info("{} 获取到结果 ：{}", Thread.currentThread().getName(), GuardedObjects.getAndDelete(id));
        }).start();

        Thread.sleep(1000);
        new Thread(() -> {
            GuardedObjects.set(1, "WangHan 牛逼");
            log.info("{} 计算到结果 ：{}", Thread.currentThread().getName(), "WangHan 牛逼");
        }).start();

        Thread.sleep(100000);
    }
}

@Slf4j
class GuardedObjects{
    private static Map<Integer, GuardedObject> guardedObjectMap = new ConcurrentHashMap<>(64);

    private static int id = 1;

    public synchronized static int generateId(){
        return id++;
    }

    public static Integer createGuardedObject(){
        GuardedObject guardedObject = new GuardedObject();
        int id = generateId();
        guardedObjectMap.put(id, guardedObject);
        return id;
    }

    public static Object getAndDelete(Integer id){
        GuardedObject guardedObject = guardedObjectMap.get(id);
        if (guardedObject.get() == null){
            return null;
        }
        guardedObjectMap.remove(id);
        return guardedObject.get();
    }

    public static Object get(Integer id){
        GuardedObject guardedObject = guardedObjectMap.get(id);
        return guardedObject.get();
    }

    public Set<Integer> getIds(){
        return guardedObjectMap.keySet();
    }

    public static void set(Integer id, String data){
        GuardedObject guardedObject = guardedObjectMap.get(id);
        while (guardedObject == null){
            try {
                Thread.sleep(1000);
                guardedObject = guardedObjectMap.get(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        guardedObject.complete(data);
    }
}

@Slf4j
class GuardedObject{
    //结果
    private  Object response;

    public Object get(){
        synchronized (this){
            while (response == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    public Object get(long timeout){
        synchronized (this){
            if (response == null){
                try {
                    //可能会存在虚假唤醒，比如别人可能在你还没等待足够长的时间就唤醒你
                    this.wait(timeout);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    //这样实现就防止了虚假唤醒的情况
    public Object getForTimeout(long timeout){
        long begin = System.currentTimeMillis();
        long processTime = 0;
        synchronized (this){
            while (response == null){
                if (processTime >= timeout){
                    break;
                }
                try {
                    //可能会存在虚假唤醒，比如别人可能在你还没等待足够长的时间就唤醒你
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis();
                processTime = endTime - begin;
            }
            return response;
        }
    }

    //产生结果
    public void complete(Object response){
        synchronized (this){
            this.response = response;
            this.notifyAll();
        }
    }
}
