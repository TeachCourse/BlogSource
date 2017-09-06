package cn.teachcourse.enums.original;

/**
 * Created by http://teachcourse.cn on 2017/9/5.
 */

public class ExcellentStudent {
    public static final ExcellentStudent ZHAOYUN = new ExcellentStudent(2011110924, "赵云", "网络工程方向");
    public static final ExcellentStudent ZHANGFEI = new ExcellentStudent(2011110925, "张飞", "信息工程方向");
    public static final ExcellentStudent LIUBEI = new ExcellentStudent(2011110926, "刘备", "数字媒体方向");
    public static final ExcellentStudent NONE = new ExcellentStudent(0, "匿名", "你猜我学什么专业？");

    private int num;
    private String name;
    private String profession;

    ExcellentStudent(int num, String name, String profession) {
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

    public String query(int num) {
        for (ExcellentStudent student : values()) {
            if (student.getNum() == num)
                return "姓名：" + student.getName() + "，" +
                        "专业：" + student.getProfession();
        }
        return "姓名：" + NONE.getName() + "，" +
                "专业：" + NONE.getProfession();
    }

    public static ExcellentStudent[] values() {
        return new ExcellentStudent[]{ZHAOYUN, ZHANGFEI, LIUBEI, NONE};
    }

    public static ExcellentStudent valueOf(String name) {
        for (ExcellentStudent student : values()) {
            if (name.equals(student.getName()))
                return student;
        }
        return NONE;
    }
}
