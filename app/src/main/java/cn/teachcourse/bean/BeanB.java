package cn.teachcourse.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by http://teachcourse.cn on 2017/6/2.
 */

public class BeanB extends BaseBean {
    @SerializedName("自然村屯")
    private List<BeanC> beanC;

    public BeanB(String name, List<BeanC> list) {
        super(name);
        this.beanC = list;
    }

    public List<BeanC> getBeanC() {
        return beanC;
    }

    public void setBeanC(List<BeanC> beanC) {
        this.beanC = beanC;
    }
}
