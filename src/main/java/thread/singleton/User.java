package thread.singleton;

import java.io.Serializable;

/**
 * 构建模式  使用构建器实例化
 */
public class User implements Serializable {

    private String name;
    private int age;
    private double high;
    private String sex;
    private String hobby;

    public static class Builder{
        private String name;
        private int age;

        private double high = 0;
        private String sex = "";
        private String hobby = "";

        public Builder(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Builder high(double high){
            this.high = high;
            return this;
        }
        public Builder sex(String sex){
            this.sex = sex;
            return this;
        }
        public Builder hobby(String hobby){
            this.hobby = hobby;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public User(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.high = builder.high;
        this.sex = builder.sex;
        this.hobby = builder.hobby;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", high=" + high +
                ", sex='" + sex + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}
