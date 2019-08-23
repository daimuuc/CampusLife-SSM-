package com.ponmma.cl.dto;

import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.enums.ShopCategoryEnum;

import java.util.List;

public class ShopCategoryExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // shopCategory
    private ShopCategory shopCategory;
    // shopCategoryList列表
    private List<ShopCategory> shopCategoryList;

    public ShopCategoryExecution() {

    }

    // 操作失败的时候使用的构造器
    public ShopCategoryExecution(ShopCategoryEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public ShopCategoryExecution(ShopCategoryEnum stateEnum, ShopCategory shopCategory) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopCategory = shopCategory;
    }

    // 操作成功的时候使用的构造器
    public ShopCategoryExecution(ShopCategoryEnum stateEnum, List<ShopCategory> shopCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopCategoryList = shopCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public void setShopCategory(ShopCategory shopCategory) {
        this.shopCategory = shopCategory;
    }

    public List<ShopCategory> getShopCategoryList() {
        return shopCategoryList;
    }

    public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
        this.shopCategoryList = shopCategoryList;
    }
}
