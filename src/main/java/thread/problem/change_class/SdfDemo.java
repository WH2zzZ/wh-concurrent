package thread.problem.change_class;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 时间格式类是线程不安全的
 * @Author WangHan
 * @Create 2020/5/20 8:21 下午
 */
@Slf4j
public class SdfDemo {

    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.info("data:{}", simpleDateFormat.parse("2020-01-02"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    /**
     * 日期不安全加锁解决
     */
    @Test
    public void test02() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (simpleDateFormat) {
                    try {
                        log.info("data:{}", simpleDateFormat.parse("2020-01-02"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * java8支持的不可变的时间格式类
     */
    @Test
    public void test03() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> log.info("data:{}", dateTimeFormatter.parse("2020-01-02"))).start();
        }
    }
}
