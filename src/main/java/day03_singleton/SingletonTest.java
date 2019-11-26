package day03_singleton;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SingletonTest {

    @Test
    public void test8(){
        for (int i = 0; i < 5; i++){
            User user = Singleton8.INSTANCE.getInstance();
            System.out.println(user);
        }
    }

    @Test
    public void serializableTest() throws Exception {
        //写入
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\objFile.obj"));

        Singleton6 instance1 = Singleton6.getInstance();

        out.writeObject(instance1);
        out.close();

        //写出
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\objFile.obj"));
        Singleton6 instance2 = (Singleton6) in.readObject();
        in = new ObjectInputStream(new FileInputStream("D:\\objFile.obj"));
        Singleton6 instance3 = (Singleton6) in.readObject();

        System.out.println(instance1);
        System.out.println(instance2);
        System.out.println(instance3);
        in.close();
    }

    @Test
    public void serializableEnumTest() throws Exception {
        //写入
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\objFile.obj"));

        Singleton8 instance1 = Singleton8.INSTANCE;

        out.writeObject(instance1);
        out.close();

        //写出
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\objFile.obj"));
        Singleton8 instance2 = (Singleton8) in.readObject();
        in = new ObjectInputStream(new FileInputStream("D:\\objFile.obj"));
        Singleton8 instance3 = (Singleton8) in.readObject();

        System.out.println(instance1.hashCode());
        System.out.println(instance2.hashCode());
        System.out.println(instance3.hashCode());
        in.close();
    }
}
