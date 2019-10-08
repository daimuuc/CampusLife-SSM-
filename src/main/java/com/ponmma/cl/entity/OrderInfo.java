package com.ponmma.cl.entity;

import java.util.Date;

public class OrderInfo {

    // 主键ID
    private Integer id;
    // 顾客信息
    private PersonInfo personInfo;
    // 商品信息
    private ProductInfo productInfo;
    // 店铺信息
    private ShopInfo shopInfo;
    // 订单状态，0：正在进行 、1：已经完成
    private Integer status;
    // 上一次编辑时间
    private Date lastEditTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", personInfo=" + personInfo +
                ", productInfo=" + productInfo +
                ", shopInfo=" + shopInfo +
                ", status=" + status +
                ", lastEditTime=" + lastEditTime +
                '}';
    }
}
