package thread.util;

import java.util.Objects;

/**
 * @Author WangHan
 * @Create 2019/12/16 10:08 下午
 */
public class QueueObject {
    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {
        while (!isNotified){
            this.wait();
        }

        this.isNotified = false;
    }

    public synchronized void doNotify(){
        this.isNotified = true;
        this.notify();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueueObject that = (QueueObject) o;
        return isNotified == that.isNotified;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isNotified);
    }
}
