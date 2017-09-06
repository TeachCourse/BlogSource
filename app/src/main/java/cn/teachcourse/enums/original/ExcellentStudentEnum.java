package cn.teachcourse.enums.original;

/**
 * Created by http://teachcourse.cn on 2017/9/5.
 */

public enum ExcellentStudentEnum {
    ZHAOYUN(2011110924, "赵云", "网络工程方向"),
    ZHANGFEI(2011110925, "张飞", "信息工程方向"),
    LIUBEI(2011110926, "刘备", "数字媒体方向"),
    NONE(0, "匿名", "你猜我学什么专业？");
    private int num;
    private String name;
    private String profession;

    ExcellentStudentEnum(int num, String name, String profession) {
        this.num = num;
        this.name = name;
        this.profession = profession;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getProfession() {
        return profession;
    }

    public static String query(int num) {
        for (ExcellentStudentEnum student : values()) {
            if (student.getNum() == num)
                return "姓名：" + student.getName() + "，" +
                        "专业：" + student.getProfession();
        }
        return "姓名：" + NONE.getName() + "，" +
                "专业：" + NONE.getProfession();
    }
}
