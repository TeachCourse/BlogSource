package cn.teachcourse.json.bean;

import java.io.Serializable;

/**
 * Created by postmaster@teachcourse.cn on 2016/5/11.
 */
public class DistrictBean implements Serializable{
    private String id;//地区的ID
    private String name;//地区全称
    private String parentId;//上一级地区ID
    private String childrenNode;//下一级地区信息

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildrenNode() {
        return childrenNode;
    }

    public DistrictBean(String id, String name, String parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public DistrictBean() {
    }

    public void setChildrenNode(String childrenNode) {
        this.childrenNode = childrenNode;
    }
}
