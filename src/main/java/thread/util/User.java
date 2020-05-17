package thread.util;

import lombok.Data;

/**
 * 用户
 *
 * @Author WangHan
 * @Create 2019/12/3 11:42 下午
 */
@Data
public class User {

    private String name;
    public volatile int age;
}
