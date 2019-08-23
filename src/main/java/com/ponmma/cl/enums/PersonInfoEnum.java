package com.ponmma.cl.enums;

public enum PersonInfoEnum {

    PHONE_EXIST(-2, "该手机号已注册该用户类型"),
    PHONE_PASSWORD_ROLE_EMPTY(-1, "手机号、密码或用户类型为空"),
    IMAGE_EMPTY(0, "图片为空"), ADD_SUCCEESS(1, "添加个人信息成功"),
    UPDATE_SUCCESS(2, "更新个人信息成功"), QUERY_SUCCESS(3, "查询个人信息成功");

    private int state;
    private String stateInfo;

    private PersonInfoEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PersonInfoEnum stateOf(int state) {
        for (PersonInfoEnum personInfoEnum : values()) {
            if (personInfoEnum.getState() == state) {
                return personInfoEnum;
            }
        }
        return null;
    }

}
