package cn.teachcourse.enums.week;

/**
 * Created by http://teachcourse.cn on 2017/9/5.
 */

public enum WeeklyPlanEnum {
    MON("周一：了解反编译apk文件的方法"),
    TUES("周二：学习防止反编译的技术——代码混淆、压缩、优化"),
    WED("周三：参考代码混淆官方文档"),
    THUR("周四：学习混淆普通的包名、类名、方法名的方法"),
    FRI("周五：学习混淆Android项目的资源文件、属性文件"),
    SAT("周六：休息、睡懒觉"),
    SUN("周日：打球");
    private String plan;

    WeeklyPlanEnum(String plan) {
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }
}
