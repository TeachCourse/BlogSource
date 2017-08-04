package cn.teachcourse.designpattern.factory;

/**
 * Created by http://teachcourse.cn on 2017/8/4.
 */

public class AdultSweater implements Product {
    @Override
    public void makeSweater() {
        System.out.println("当前生产的毛衣：");
        System.out.println("尺寸：" + "成人大小");
        System.out.println("样式：" + "夏装男士圆领净色纯色短T男装");
        System.out.println("材料：" + "纯棉体恤");
    }
}
