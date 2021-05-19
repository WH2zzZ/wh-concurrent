package thread.problem.threadlocal;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author WangHan
 * @Create 2020/5/25 1:32 上午
 */
@Slf4j
public class Demo01 {

    public static void main(String[] args) {

        Content content = new Content();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                content.set(finalI + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Thread:{}.data:{}", Thread.currentThread().getName(), content.getData());
            }).start();
        }
    }
}

class Content{

    private ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    public String getData() {
        return stringThreadLocal.get();
    }

    public void set(String data) {
        stringThreadLocal.set(data);
    }
}
