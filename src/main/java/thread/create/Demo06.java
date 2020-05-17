package thread.create;


import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;

/**
 * 线程池
 */
public class Demo06 {

    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 1L, TimeUnit.SECONDS, new BlockingQueue<Runnable>() {
            @Override
            public boolean add(Runnable runnable) {
                System.out.println("添加阻塞的任务");
                return false;
            }

            @Override
            public boolean offer(Runnable runnable) {
                System.out.println("offer run");
                return false;
            }

            @Override
            public Runnable remove() {
                return null;
            }

            @Override
            public void put(Runnable runnable) throws InterruptedException {
                System.out.println("put run");
            }

            @Override
            public boolean offer(Runnable runnable, long timeout, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public Runnable take() throws InterruptedException {
                return null;
            }

            @Override
            public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
                return null;
            }

            @Override
            public int remainingCapacity() {
                return 0;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public int drainTo(Collection<? super Runnable> c) {
                return 0;
            }

            @Override
            public int drainTo(Collection<? super Runnable> c, int maxElements) {
                return 0;
            }

            @Override
            public Runnable poll() {
                return null;
            }

            @Override
            public Runnable element() {
                return null;
            }

            @Override
            public Runnable peek() {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public Iterator<Runnable> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Runnable> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }
        });
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(() -> System.out.println(Thread.currentThread().getName()));
        }
        threadPoolExecutor.shutdown();


//        ExecutorService threadPool = Executors.newCachedThreadPool();
//        ExecutorService threadPool = Executors.newFixedThreadPool(10);

//        for (int i = 0; i < 1000000; i++) {
//            threadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
//        }
//
        //不关闭，就不会停
//        threadPool.shutdown();
    }
}
