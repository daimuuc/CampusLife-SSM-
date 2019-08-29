package com.ponmma.cl.dto;

import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.enums.ProductInfoEnum;

import java.util.List;

public class ProductInfoExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // productInfo
    private ProductInfo productInfo;
    // productInfoList列表
    private List<ProductInfo> productInfoList;

    public ProductInfoExecution() {

    }

    // 操作失败的时候使用的构造器
    public ProductInfoExecution(ProductInfoEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public ProductInfoExecution(ProductInfoEnum stateEnum, ProductInfo productInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productInfo = productInfo;
    }

    // 操作成功的时候使用的构造器
    public ProductInfoExecution(ProductInfoEnum stateEnum, List<ProductInfo> productInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productInfoList = productInfoList;
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

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }
}
