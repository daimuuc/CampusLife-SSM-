package com.ponmma.cl.entity;

public class CartInfo {
    // 主键
    private int id;
    // 个人信息
    private PersonInfo personInfo;
    // 产品信息
    private ProductInfo productInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "CartInfo{" +
                "id=" + id +
                ", personInfo=" + personInfo +
                ", productInfo=" + productInfo +
                '}';
    }
}
