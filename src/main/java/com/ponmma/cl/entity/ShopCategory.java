package com.ponmma.cl.entity;

import java.util.Date;

// 商铺类别
public class ShopCategory {

    // 主键，ID
    private Integer id;
    // 商铺类别名称
    private String name;
    // 上一次编辑时间
    private Date lastEditTime;
    // 类别图片
    private SingleImageInfo singleImageInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public SingleImageInfo getSingleImageInfo() {
        return singleImageInfo;
    }

    public void setSingleImageInfo(SingleImageInfo singleImageInfo) {
        this.singleImageInfo = singleImageInfo;
    }

    @Override
    public String toString() {
        return "ShopCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastEditTime=" + lastEditTime +
                ", singleImageInfo=" + singleImageInfo +
                '}';
    }
}
