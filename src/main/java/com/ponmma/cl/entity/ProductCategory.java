package com.ponmma.cl.entity;

import java.util.Date;

// 商品类别信息
public class ProductCategory {
    // 主键
    private Integer id;
    // 名字
    private String name;
    // 最后一次编辑时间
    Date lastEditTime;
    // 商铺信息
    Integer shopInfoId;

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastEditTime=" + lastEditTime +
                ", shopInfoId=" + shopInfoId +
                '}';
    }

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

    public Integer getShopInfoId() {
        return shopInfoId;
    }

    public void setShopInfoId(Integer shopInfoId) {
        this.shopInfoId = shopInfoId;
    }
}

