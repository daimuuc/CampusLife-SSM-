package com.ponmma.cl.dto;



import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.ShopInfoEnum;

import java.util.List;

public class ShopInfoExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // shopInfo
    private ShopInfo shopInfo;
    // shopInfoList列表
    private List<ShopInfo> shopInfoList;

    public ShopInfoExecution() {

    }

    // 操作失败的时候使用的构造器
    public ShopInfoExecution(ShopInfoEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public ShopInfoExecution(ShopInfoEnum stateEnum, ShopInfo shopInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopInfo = shopInfo;
    }

    // 操作成功的时候使用的构造器
    public ShopInfoExecution(ShopInfoEnum stateEnum, List<ShopInfo> shopInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopInfoList = shopInfoList;
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

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public List<ShopInfo> getShopInfoList() {
        return shopInfoList;
    }

    public void setShopInfoList(List<ShopInfo> shopInfoList) {
        this.shopInfoList = shopInfoList;
    }
}
