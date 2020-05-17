package thread.singleton;

import java.io.Serializable;

/**
 * 双重检查[推荐用]
 */

public class Singleton6 implements Serializable {

    private static Singleton6 instance;

    private Singleton6(){}

    public static Singleton6 getInstance(){
        if (instance == null){
            synchronized (Singleton6.class){
                if (instance == null){
                    instance = new Singleton6();
                }
            }
        }
        return instance;
    }

    /**
     * 保证序列化之后任然是单例"一定要用Object接收"
     * 显然此时被序列化过后还是单例，至于为什么要实现readResolve，
     * 这个方法并不是Serializable接口的方法，在此我并没有深究，
     * 究其原因估计是在反序列化的时候会调用这个奇怪的方法。
     */
    private Object readResolve(){
        return instance;
    }
}
