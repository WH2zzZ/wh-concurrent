package thread.create;

/**
 * 线程内部类（无返回值）
 */
public class Demo03 {

    public static void main(String[] args) {
        new Thread(() -> System.out.println("thread start ...")).start();


        new Thread(() -> System.out.println("thread2 start ...")).start();

        //匿名内部类，加上重写thread的run方法，后面的最终执行
        new Thread(() -> System.out.println("thread3 start ...")) {
            @Override
            public void run() {
                System.out.println("new thread3 start ...");
            };
        }.start();

    }
}
