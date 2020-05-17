package thread.problem.safe.synchronizeds;

/**
 * javap -verbose Synchronized04 查看字节码文件
 *
 * public int getNext();
 *     descriptor: ()I
 *     flags: ACC_PUBLIC
 *     Code:
 *       stack=3, locals=3, args_size=1
 *          0: aload_0
 *          1: dup
 *          2: astore_1
 *          3: monitorenter                      //开启锁
 *          4: getstatic     #2                  // Field value:I
 *          7: dup
 *          8: iconst_1
 *          9: iadd
 *         10: putstatic     #2                  // Field value:I
 *         13: aload_1
 *         14: monitorexit                       //推出锁
 *         15: ireturn
 *         16: astore_2
 *         17: aload_1
 *         18: monitorexit
 *         19: aload_2
 *         20: athrow
 *       Exception table:
 *          from    to  target type
 *              4    15    16   any
 *             16    19    16   any
 *       LineNumberTable:
 *         line 28: 0
 *         line 29: 4
 *         line 30: 16
 *       LocalVariableTable:
 *         Start  Length  Slot  Name   Signature
 *             0      21     0  this   Lthread/problem/safe/Synchronized04;
 *       StackMapTable: number_of_entries = 1
 *          frame_type = 255 // full_frame
 *          offset_delta=16
 *          locals=[class thread/problem/safe/Synchronized04,class java/lang/Object]
 *          stack=[class java/lang/Throwable]
 **/

/**
 * Synchronized指定锁对象
 * @Author WangHan
 * @Create 5:00 下午 2019/12/1
 */
public class Synchronized04 {

    private static int value;

    /**
     * 指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。
     * 将synchronized作用于一个给定的实例对象instance，即当前实例对象就是锁对象，每次当线程进入synchronized包裹的代码块时就会要求当前线程持有instance实例对象锁，
     * 如果当前有其他线程正持有该对象锁，那么新到的线程就必须等待，这样也就保证了每次只有一个线程执行i++;操作。
     * 当然除了instance作为对象外，我们还可以使用this对象(代表当前实例)或者当前类的class对象作为锁
     * @return
     */
    public int getNext(){
        /**
         * Java 虚拟机中的同步(Synchronization)基于进入和退出管程(Monitor)对象实现， 无论是显式同步(有明确的 monitorenter 和 monitorexit 指令,即同步代码块)还是隐式同步都是如此。
         * 在 Java 语言中，同步用的最多的地方可能是被 synchronized 修饰的同步方法。
         * 同步方法 并不是由 monitorenter 和 monitorexit 指令来实现同步的，而是由方法调用指令读取运行时常量池中方法的 ACC_SYNCHRONIZED 标志来隐式实现的
         */
        // monitorenter 将MarkWord置为Monitor指针
        synchronized (this) {
            return value++;
        }
        // monitorexit 将MarkWord还原，唤醒EntryList
    }

    public static void main(String[] args) {
        Synchronized04 sequence = new Synchronized04();

        makeThread(sequence);

        makeThread(sequence);

    }

    private static void makeThread(Synchronized04 sequence) {
        new Thread(() -> {
            while (true) {
                System.out.println(Thread.currentThread().getName() + " " + sequence.getNext());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
