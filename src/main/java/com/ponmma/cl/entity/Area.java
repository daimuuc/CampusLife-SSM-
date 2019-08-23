package com.ponmma.cl.entity;

import java.util.Date;

// 区域信息
public class Area {
    // 主键，ID
    private Integer id;
    // 区域名称
    private String name;
    // 上一次编辑时间
    private Date lastEditTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastEditTime=" + lastEditTime +
                '}';
    }
}
