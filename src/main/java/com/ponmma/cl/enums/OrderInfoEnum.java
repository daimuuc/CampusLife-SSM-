package com.ponmma.cl.enums;

public enum OrderInfoEnum {

    ADD_FAILURE(-1, "添加失败"), ADD_SUCCESS(1, "添加成功"), QUERY_SUCCESS(2, "查询成功"), DELETE_SUCCESS(3, "删除成功");

    private int state;
    private String stateInfo;

    private OrderInfoEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OrderInfoEnum stateOf(int state) {
        for (OrderInfoEnum cartInfoEnum : values()) {
            if (cartInfoEnum.getState() == state) {
                return cartInfoEnum;
            }
        }
        return null;
    }

}
