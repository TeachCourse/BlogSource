package cn.teachcourse.designpattern.factory;

/**
 * Created by http://teachcourse.cn on 2017/8/4.
 */

public class OldSweater implements Product {
    @Override
    public void makeSweater() {
        System.out.println("当前生产的毛衣：");
        System.out.println("尺寸：" + "老人大小");
        System.out.println("样式：" + "中老年男装夹克2017春季新款");
        System.out.println("材料：" + "锦纶");
    }
}
