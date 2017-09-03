package cn.teachcourse.advancedcourse_1.bean;

import java.io.Serializable;

/**
 * Created by TeachCourse.cn on 2017/9/3 00:51.
 */

public class Subject implements Serializable {
    private String courseName;//课程名称
    private String creditHour;//课程学分

    public Subject(String courseName, String creditHour) {
        this.courseName = courseName;
        this.creditHour = creditHour;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCreditHour() {
        return creditHour;
    }

    public void setCreditHour(String creditHour) {
        this.creditHour = creditHour;
    }
}
