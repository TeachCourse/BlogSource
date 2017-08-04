package cn.teachcourse.designpattern.factory;

/**
 * Created by http://teachcourse.cn on 2017/8/4.
 */

public class AdultFactory extends SweaterFactory {
    @Override
    public Product createProduct() {
        return new AdultSweater();
    }
}
