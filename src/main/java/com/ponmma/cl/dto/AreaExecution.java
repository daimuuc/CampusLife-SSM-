package com.ponmma.cl.dto;

import com.ponmma.cl.entity.Area;
import com.ponmma.cl.enums.AreaEnum;

import java.util.List;

public class AreaExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // area
    private Area area;
    // areaList列表
    private List<Area> areaList;

    public AreaExecution() {

    }

    // 操作失败的时候使用的构造器
    public AreaExecution(AreaEnum areaEnum) {
        this.state = areaEnum.getState();
        this.stateInfo = areaEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public AreaExecution(AreaEnum stateEnum, Area area) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.area = area;
    }

    // 操作成功的时候使用的构造器
    public AreaExecution(AreaEnum stateEnum, List<Area> areaList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.areaList = areaList;
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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }
}
