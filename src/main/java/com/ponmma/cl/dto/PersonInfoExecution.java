package com.ponmma.cl.dto;

import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.enums.PersonInfoEnum;

import java.util.List;

public class PersonInfoExecution {

    // 结果状态
    private int state;
    // 状态标识
    private String stateInfo;
    // 数量
    private int count;
    // personInfo
    private PersonInfo personInfo;
    // personInfo列表
    private List<PersonInfo> personInfoList;

    public PersonInfoExecution() {

    }

    // 操作失败的时候使用的构造器
    public PersonInfoExecution(PersonInfoEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 操作成功的时候使用的构造器
    public PersonInfoExecution(PersonInfoEnum stateEnum, PersonInfo personInfo) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.personInfo = personInfo;
    }

    // 操作成功的时候使用的构造器
    public PersonInfoExecution(PersonInfoEnum stateEnum, List<PersonInfo> personInfoList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.personInfoList = personInfoList;
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

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(PersonInfo personInfo) {
        this.personInfo = personInfo;
    }

    public List<PersonInfo> getPersonInfoList() {
        return personInfoList;
    }

    public void setPersonInfoList(List<PersonInfo> personInfoList) {
        this.personInfoList = personInfoList;
    }
}
