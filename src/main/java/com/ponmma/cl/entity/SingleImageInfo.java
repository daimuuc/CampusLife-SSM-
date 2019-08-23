package com.ponmma.cl.entity;

// 单一图片信息
public class SingleImageInfo {

    @Override
    public String toString() {
        return "SingleImageInfo{" +
                "id=" + id +
                ", src='" + src + '\'' +
                '}';
    }

    // 主键ID
    private Integer id;
    // 图片地址
    private String src;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
