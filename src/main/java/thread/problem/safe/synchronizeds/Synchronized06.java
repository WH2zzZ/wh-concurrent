package thread.problem.safe.synchronizeds;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 *
 */
@Slf4j
public class Synchronized06 {


    @Test
    public void test(){
        Dog dog = new Dog();
        ClassLayout.parseInstance(dog).toPrintable();
    }
}
class Dog{

}