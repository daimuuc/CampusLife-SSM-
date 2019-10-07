package com.ponmma.cl.dto;

import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.enums.CartInfoEnum;

import java.util.List;

public class OrderInfoExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // cartInfo
    private CartInfo cartInfo;
    // cartInfoList列表
    private List<CartInfo> cartInfoList;

    public OrderInfoExecution() {

    }

    // 操作失败的时候使用的构造器
    public OrderInfoExecution(CartInfoEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public OrderInfoExecution(CartInfoEnum stateEnum, CartInfo cartInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.cartInfo = cartInfo;
    }

    // 操作成功的时候使用的构造器
    public OrderInfoExecution(CartInfoEnum stateEnum, List<CartInfo> cartInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.cartInfoList = cartInfoList;
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

    public CartInfo getCartInfo() {
        return cartInfo;
    }

    public void setCartInfo(CartInfo cartInfo) {
        this.cartInfo = cartInfo;
    }

    public List<CartInfo> getCartInfoList() {
        return cartInfoList;
    }

    public void setCartInfoList(List<CartInfo> cartInfoList) {
        this.cartInfoList = cartInfoList;
    }
}
