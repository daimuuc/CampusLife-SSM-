package com.ponmma.cl.enums;

public enum ShopInfoEnum {
    ADD_SUCCESS(1, "添加成功"), UPDATE_SUCCESS(2, "修改成功"),
    QUERY_SUCCESS(3, "查询成功");

    private int state;
    private String stateInfo;

    private ShopInfoEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopInfoEnum stateOf(int state) {
        for (ShopInfoEnum shopInfoEnum : values()) {
            if (shopInfoEnum.getState() == state) {
                return shopInfoEnum;
            }
        }
        return null;
    }

}
