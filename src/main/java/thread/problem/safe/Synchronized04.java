package thread.problem.safe;

import org.junit.jupiter.api.Test;

/**
 * synchronized原理
 * 内置琐
 * 任何对象都可以作为琐, 那么琐信息存在对象的什么地方?
 *      存在对象头中
 * 对象头中的信息
 *      Mark Word(Hash 和 锁信息(线程id Epoch  对象的分代年龄信息  是否是偏向琐  琐标志位) 等....)
 *      Class Metadata Address(类的对象的地址)
 *      Array Length
 * 偏向琐
 *      在大多数情况下，锁不仅不存在多线程竞争，而且总是由同一线程多次获得，因此为了减少同一线程获取锁(会涉及到一些CAS操作,耗时)的代价而引入偏向锁。
 *      偏向锁的核心思想是，如果一个线程获得了锁，那么锁就进入偏向模式，此时Mark Word 的结构也变为偏向锁结构，
 *      当这个线程再次请求锁时，无需再做任何同步操作，即获取锁的过程，这样就省去了大量有关锁申请的操作，从而也就提供程序的性能。
 *      所以，对于没有锁竞争的场合，偏向锁有很好的优化效果，毕竟极有可能连续多次是同一个线程申请相同的锁。
 *      但是对于锁竞争比较激烈的场合，偏向锁就失效了，因为这样场合极有可能每次申请锁的线程都是不相同的，因此这种场合下不应该使用偏向锁，否则会得不偿失，
 *      需要注意的是，偏向锁失败后，并不会立即膨胀为重量级锁，而是先升级为轻量级锁。
 * 轻量级琐
 *      倘若偏向锁失败，虚拟机并不会立即升级为重量级锁，它还会尝试使用一种称为轻量级锁的优化手段(1.6之后加入的)，此时Mark Word 的结构也变为轻量级锁的结构。
 *      轻量级锁能够提升程序性能的依据是“对绝大部分的锁，在整个同步周期内都不存在竞争”，注意这是经验数据。
 *      需要了解的是，轻量级锁所适应的场景是线程交替执行同步块的场合，如果存在同一时间访问同一锁的场合，就会导致轻量级锁膨胀为重量级锁。
 * 自旋锁
 *      轻量级锁失败后，虚拟机为了避免线程真实地在操作系统层面挂起，还会进行一项称为自旋锁的优化手段。
 *      这是基于在大多数情况下，线程持有锁的时间都不会太长，如果直接挂起操作系统层面的线程可能会得不偿失，
 *      毕竟操作系统实现线程之间的切换时需要从用户态转换到核心态，这个状态之间的转换需要相对比较长的时间，时间成本相对较高，
 *      因此自旋锁会假设在不久将来，当前的线程可以获得锁，因此虚拟机 会让当前想要获取锁的线程做几个空循环(这也是称为自旋的原因)，
 *      一般不会太久，可能是50个循环或100循环，在经过若干次循环后，如果得到锁，就顺利进入临界区。
 *      如果还不能获得锁，那就会将线程在操作系统层面挂起，这就是自旋锁的优化方式，这种方式确实也是可以提升效率的。
 * 锁消除
 *      消除锁是虚拟机另外一种锁的优化，这种优化更彻底，Java虚拟机在JIT编译时(可以简单理解为当某段代码即将第一次被执行时进行编译，又称即时编译)，
 *      通过对运行上下文的扫描，去除不可能存在共享资源竞争的锁，通过这种方式消除没有必要的锁，可以节省毫无意义的请求锁时间，
 *      如下StringBuffer的append是一个同步方法，但是在add方法中的StringBuffer属于一个局部变量，并且不会被其他线程所使用，
 *      因此StringBuffer不可能存在共享资源竞争的情景，JVM会自动将其锁消除。
 *
 *      消除StringBuffer同步锁
        public class StringBufferRemoveSync {

            public void add(String str1, String str2) {
                //StringBuffer是线程安全,由于sb只会在append方法中使用,不可能被其他线程引用
                //因此sb属于不可能共享的资源,JVM会自动消除内部的锁
                StringBuffer sb = new StringBuffer();
                sb.append(str1).append(str2);
            }

            public static void main(String[] args) {
                StringBufferRemoveSync rmsync = new StringBufferRemoveSync();
                for (int i = 0; i < 10000000; i++) {
                    rmsync.add("abc", "123");
                }
            }
        }
 * 重量级琐
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
        // monitorenter
        synchronized (this) {
            return value++;
        }
        // monitorexit
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
