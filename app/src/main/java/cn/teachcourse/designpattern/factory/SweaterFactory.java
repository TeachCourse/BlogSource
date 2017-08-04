package cn.teachcourse.designpattern.factory;

/**
 * Created by http://teachcourse.cn on 2017/8/4.
 */

public abstract class SweaterFactory {

    public abstract Product createProduct();

    public static Product createProduct(String type) {
        Product product;
        switch (type) {
            case "children":

                product = new ChildrenSweater();

                break;
            case "adult":

                product = new AdultSweater();

                break;
            case "old":

                product = new OldSweater();

                break;
            default:

                product = new ChildrenSweater();

                break;
        }
        return product;
    }

    public static <T extends Product> T createProduct(Class<T> clz) {
        Product product = null;
        try {
            product = (Product) Class.forName(clz.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (T) product;

    }

    public static Sweater createSweater(String size, String style, String material) {
        return new Sweater(size, style, material);
    }
}
