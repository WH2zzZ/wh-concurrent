package thread.problem.safe.synchronizeds;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.locks.Lock;

@Slf4j
public class JolTest {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(Lock.class).toPrintable());
    }
}