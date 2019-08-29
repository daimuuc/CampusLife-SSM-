package com.ponmma.cl.entity;

// 多张图片信息
public class MutipleImageInfo {

    // 主键
    private Integer id;
    // 图片地址
    private String src;
    // 商品信息
    private Integer productInfoId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getProductInfoId() {
        return productInfoId;
    }

    public void setProductInfoId(Integer productInfoId) {
        this.productInfoId = productInfoId;
    }

    @Override
    public String toString() {
        return "MutipleImageInfo{" +
                "id=" + id +
                ", src='" + src + '\'' +
                ", productInfoId=" + productInfoId +
                '}';
    }
}
