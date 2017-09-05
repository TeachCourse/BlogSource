package cn.teachcourse.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by http://teachcourse.cn on 2017/6/2.
 */

public class BeanA extends BaseBean {
    @SerializedName("行政村")
    private List<BeanB> beanB;

    public BeanA(String name, List<BeanB> list) {
        super(name);
        this.beanB = list;
    }

    public List<BeanB> getBeanB() {
        return beanB;
    }

    public void setBeanB(List<BeanB> beanB) {
        this.beanB = beanB;
    }
}
