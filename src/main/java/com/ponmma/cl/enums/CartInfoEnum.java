package com.ponmma.cl.enums;

public enum CartInfoEnum {

    ADD_SUCCESS(1, "添加成功"), QUERY_SUCCESS(2, "查询成功");

    private int state;
    private String stateInfo;

    private CartInfoEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static CartInfoEnum stateOf(int state) {
        for (CartInfoEnum areaEnum : values()) {
            if (areaEnum.getState() == state) {
                return areaEnum;
            }
        }
        return null;
    }

}
