package day01_thread;

import java.util.Arrays;
import java.util.List;

/**
 * lambada表达式
 */
public class Demo07 {

    public int add(List<Integer> values){
        values.stream().forEach(System.out::println);
        System.out.println();
        //并行流
        values.parallelStream().forEach(System.out::println);
        return values.stream().mapToInt(value -> value).sum();
    }

    public static void main(String[] args) {
        List<Integer> values = Arrays.asList(10,20,30,40);

        int result = new Demo07().add(values);

    }
}
