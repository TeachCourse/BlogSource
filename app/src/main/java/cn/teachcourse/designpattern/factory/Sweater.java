package cn.teachcourse.designpattern.factory;

/**
 * Created by http://teachcourse.cn on 2017/8/4.
 */

public class Sweater {
    private String size;
    private String style;
    private String material;

    public Sweater(String size, String style, String material) {
        this.size = size;
        this.style = style;
        this.material = material;
    }

    public void makeSweater() {
        System.out.println("当前生产的毛衣：");
        System.out.println("尺寸：" + size);
        System.out.println("样式：" + style);
        System.out.println("材料：" + material);
    }
}
