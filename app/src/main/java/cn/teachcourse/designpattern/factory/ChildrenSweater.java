package cn.teachcourse.designpattern.factory;

/**
 * Created by http://teachcourse.cn on 2017/8/4.
 */

public class ChildrenSweater implements Product {
    @Override
    public void makeSweater() {
        System.out.println("当前生产的毛衣：");
        System.out.println("尺寸：" + "童装大小");
        System.out.println("样式：" + "松绿色");
        System.out.println("材料：" + "纯棉体恤");
    }
}
