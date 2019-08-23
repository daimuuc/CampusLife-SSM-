package com.ponmma.cl.dto;

import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.enums.ProductCategoryEnum;

import java.util.List;

public class ProductCategoryExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // productCategory
    private ProductCategory productCategory;
    // productCategoryList列表
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {

    }

    // 操作失败的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryEnum stateEnum, ProductCategory productCategory) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategory = productCategory;
    }

    // 操作成功的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryEnum stateEnum, List<ProductCategory> productCategoryList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
