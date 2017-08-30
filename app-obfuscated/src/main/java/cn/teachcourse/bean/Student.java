package cn.teachcourse.bean;

/**
 * Created by http://teachcourse.cn on 2017/8/30.
 */

public class Student {
    private String name;
    private String age;
    private String college;

    public Student(Student.Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.college = builder.college;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getCollege() {
        return college;
    }

    public static class Builder {
        private String name = "钊林";
        private String age = "24";
        private String college = "百色学院";

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        public Builder setCollege(String college) {
            this.college = college;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
