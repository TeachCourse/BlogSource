package cn.teahcourse.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by http://teachcourse.cn on 2017/8/15.
 */
@Entity(indexes = {
        @Index(value = "name, age,grade DESC", unique = true)
})
public class Student {
    @Id
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private int age;
    @NotNull
    private String grade;

    @Generated(hash = 827539117)
    public Student(Long id, @NotNull String name, int age, @NotNull String grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGrade() {
        return this.grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "姓名："+name+"；年龄："+age+"；年级："+grade;
    }
}
