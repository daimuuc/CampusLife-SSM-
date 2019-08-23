package com.ponmma.cl.entity;

// 商铺信息
public class ShopInfo {

    // 主键
    private Integer id;
    // 商铺描述
    private String des;
    // 商铺地址
    private String addr;
    // 商主信息
    private PersonInfo personInfo;
    // 商铺类别
    private ShopCategory shopCategory;
    // 商铺区域信息
    private Area area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "ShopInfo{" +
                "id=" + id +
                ", desc='" + des + '\'' +
                ", addr='" + addr + '\'' +
                ", personInfo=" + personInfo +
                ", shopCategory=" + shopCategory +
                ", area=" + area +
                '}';
    }
}