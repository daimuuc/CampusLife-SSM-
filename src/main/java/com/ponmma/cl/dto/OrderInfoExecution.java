package com.ponmma.cl.dto;

import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.entity.OrderInfo;
import com.ponmma.cl.enums.OrderInfoEnum;

import java.util.List;

public class OrderInfoExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // orderInfo
    private OrderInfo orderInfo;
    // orderInfoList列表
    private List<OrderInfo> orderInfoList;

    public OrderInfoExecution() {

    }

    // 操作失败的时候使用的构造器
    public OrderInfoExecution(OrderInfoEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public OrderInfoExecution(OrderInfoEnum stateEnum, OrderInfo orderInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.orderInfo = orderInfo;
    }

    // 操作成功的时候使用的构造器
    public OrderInfoExecution(OrderInfoEnum stateEnum, List<OrderInfo> orderInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.orderInfoList = orderInfoList;
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

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<OrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<OrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}
