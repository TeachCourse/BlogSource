package cn.teachcourse.bean;

/**
 * Created by postmaster@teachcourse.cn on 2017/4/22.
 */

public class Student {
    public String SCHOOL="北京大学";
    protected String name;
    private String age;
    float grade;

    public String information(){
        return name+"来自"+SCHOOL;
    }
}
