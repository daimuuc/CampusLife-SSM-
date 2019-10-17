package com.ponmma.cl.dto;

import com.ponmma.cl.entity.Area;
import com.ponmma.cl.entity.HeadLine;
import com.ponmma.cl.enums.AreaEnum;
import com.ponmma.cl.enums.HeadLineEnum;

import java.util.List;

public class HeadLineExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // headLine
    private HeadLine headLine;
    // headLineList列表
    private List<HeadLine> headLineList;

    public HeadLineExecution() {

    }

    // 操作失败的时候使用的构造器
    public HeadLineExecution(HeadLineEnum headLineEnum) {
        this.state = headLineEnum.getState();
        this.stateInfo = headLineEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public HeadLineExecution(HeadLineEnum stateEnum, HeadLine headLine) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.headLine = headLine;
    }

    // 操作成功的时候使用的构造器
    public HeadLineExecution(HeadLineEnum stateEnum, List<HeadLine> headLineList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.headLineList = headLineList;
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

    public HeadLine getHeadLine() {
        return headLine;
    }

    public void setHeadLine(HeadLine headLine) {
        this.headLine = headLine;
    }

    public List<HeadLine> getHeadLineList() {
        return headLineList;
    }

    public void setHeadLineList(List<HeadLine> headLineList) {
        this.headLineList = headLineList;
    }
}
