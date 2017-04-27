package cn.teachcourse.bean;

/**
 * Created by postmaster@teachcourse.cn on 2017/4/22.
 */

public class TextView{

    public void displayInfo(Student stu){
        System.out.println("显示学生信息："+stu.information());
    }

    public Student getStudent(String name,float gradle){
        Student student=new Student();
        student.name=name;
        student.grade=gradle;
        return student;
    }
}
