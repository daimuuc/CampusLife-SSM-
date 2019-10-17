package com.ponmma.cl.entity;

import java.util.Date;

public class HeadLine {
    // 主键
    private Integer id;
    // 上一次编辑时间
    private Date lastEditTime;
    // 图片信息
    private SingleImageInfo singleImageInfo;
    // 商铺信息
    private ShopInfo shopInfo;
    // 头条状态 0、申请中 1、已通过
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SingleImageInfo getSingleImageInfo() {
        return singleImageInfo;
    }

    public void setSingleImageInfo(SingleImageInfo singleImageInfo) {
        this.singleImageInfo = singleImageInfo;
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "HeadLine{" +
                "id=" + id +
                ", lastEditTime=" + lastEditTime +
                ", singleImageInfo=" + singleImageInfo +
                ", shopInfo=" + shopInfo +
                ", status=" + status +
                '}';
    }
}
