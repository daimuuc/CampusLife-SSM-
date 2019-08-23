package com.ponmma.cl.entity;

// 个人基础信息
public class PersonInfo {

    // 主键ID
    private Integer id;
    // 账户名
    private String name;
    // 手机号
    private String phone;
    // 密码
    private String password;
    // 用户类型 1)顾客 2)商家 3)管理员
    private Integer role;
    // 头像图片
    private SingleImageInfo singleImageInfo;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public SingleImageInfo getSingleImageInfo() {
        return singleImageInfo;
    }

    public void setSingleImageInfo(SingleImageInfo singleImageInfo) {
        this.singleImageInfo = singleImageInfo;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", singleImageInfo=" + singleImageInfo +
                '}';
    }
}
