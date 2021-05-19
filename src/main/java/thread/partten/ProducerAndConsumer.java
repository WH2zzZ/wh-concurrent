package thread.partten;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.UUID;

/**
 * @Author WangHan
 * @Create 2020/5/17 1:09 下午
 */
@Slf4j
public class ProducerAndConsumer {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(10);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                int a = 1;
                while (a < 100){
                    //等待0.1s产生消息
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.setId(a++);
                    message.setObject(Thread.currentThread().getName());
                    messageQueue.put(message);
                }
            }).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                while (true){
                    Message message = messageQueue.take();
                    log.info("接收到消息，message:{}", message);
                }
            }).start();

        }
    }

}


/**
 * 消息队列
 * @Author WangHan
 * @Create 1:15 下午 2020/5/17
 */
@Slf4j
class MessageQueue{

    private LinkedList<Message> container = new LinkedList<>();

    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     * 获取消息
     */
    public Message take(){
        synchronized (container){
            while (container.isEmpty()){
                try {
                    log.info("队列为空陷入等待");
                    container.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = container.removeFirst();
            container.notifyAll();
            return message;
        }
    }

    /**
     * 存入消息
     */
    public void put(Message message){
        synchronized (container){
            while (container.size() == capcity){
                try {
                    log.info("队列已满等待消费");
                    container.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            container.addLast(message);
            container.notifyAll();
        }
    }
}

@Data
final class Message{
    private int id;

    private Object object;
}

/**
 * 生产者
 */
class  Producer{

}

/**
 * 消费者
 * @Author WangHan
 * @Create 1:15 下午 2020/5/17
 */
class Consumer{

}