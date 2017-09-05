package cn.teachcourse.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by http://teachcourse.cn on 2017/6/2.
 */

public class BeanC extends BaseBean {
    @SerializedName("村民小组")
    private List<BeanD> beanD;


    public BeanC(String name, List<BeanD> list) {
        super(name);
        this.beanD = list;
    }

    public List<BeanD> getBeanD() {
        return beanD;
    }

    public void setBeanD(List<BeanD> beanD) {
        this.beanD = beanD;
    }
}
