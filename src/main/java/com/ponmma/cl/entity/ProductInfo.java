package com.ponmma.cl.entity;

import java.util.Date;
import java.util.List;

// 商品信息
public class ProductInfo {

    // 主键
    private Integer id;
    // 商品名字
    private String name;
    // 上一次编辑时间
    private Date lastEditTime;
    // 商品描述
    private String des;
    // 原价
    private Integer normalPrice;
    // 现价
    private Integer promotionPrice;
    // 积分
    private Integer point;
    // 上、下架，0、下架，1、上架
    private Integer enableStatus;

    // 店铺信息
    private ShopInfo shopInfo;
    // 缩略图
    private SingleImageInfo singleImageInfo;
    // 商品类别
    private ProductCategory productCategory;
    // 商品详情图
    private List<MutipleImageInfo> mutipleImageInfoList;

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Integer getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Integer normalPrice) {
        this.normalPrice = normalPrice;
    }

    public Integer getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Integer promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public SingleImageInfo getSingleImageInfo() {
        return singleImageInfo;
    }

    public void setSingleImageInfo(SingleImageInfo singleImageInfo) {
        this.singleImageInfo = singleImageInfo;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<MutipleImageInfo> getMutipleImageInfoList() {
        return mutipleImageInfoList;
    }

    public void setMutipleImageInfoList(List<MutipleImageInfo> mutipleImageInfoList) {
        this.mutipleImageInfoList = mutipleImageInfoList;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastEditTime=" + lastEditTime +
                ", des='" + des + '\'' +
                ", normalPrice=" + normalPrice +
                ", promotionPrice=" + promotionPrice +
                ", point=" + point +
                ", enableStatus=" + enableStatus +
                ", shopInfo=" + shopInfo +
                ", singleImageInfo=" + singleImageInfo +
                ", productCategory=" + productCategory +
                ", mutipleImageInfoList=" + mutipleImageInfoList +
                '}';
    }
}
