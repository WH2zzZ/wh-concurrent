package thread.problem.safe.collection;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WangHan
 * @Create 2020/5/24 5:36 下午
 */
@Slf4j
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put(1, 1);
        concurrentHashMap.get(1);
        concurrentHashMap.computeIfAbsent(1, key -> (int)key + 3);
        System.out.println(concurrentHashMap);
    }
}
