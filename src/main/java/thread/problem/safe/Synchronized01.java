package thread.problem.safe;

/**
 * 多线程共享资源
 * 对资源进行非原子性操作(对应字节码中非一条运行就结束的操作)
 */
public class Synchronized01 {

    public synchronized int getNext(){
        /**
         * 加锁相当于把这个方法变成一个原子性操作
         * 在字节码当中value++其实是两步操作
         * 第一步把value的值+1,
         * 然后把这个值再赋值给value
         * 这两布操作就会造成线程安全性问题
         */
        return value++;
    }

    private int value;

    public static void main(String[] args) {
        Synchronized01 sequence = new Synchronized01();
//        /**
//        * 单线程无线程安全问题,顺序增加
//        */
//        while (true){
//            System.out.println(sequence.getNext());
//        }

        makeThread(sequence);

        makeThread(sequence);

    }

    private static void makeThread(Synchronized01 sequence) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
