package com.ponmma.cl.enums;

public enum HeadLineEnum {

    UPDATE_FAILURE(-3, "更新失败"), REMOVE_FAILURE(-2, "删除失败"), ADD_FAILURE(-1, "添加失败"),
    ADD_SUCCESS(1, "添加成功"), QUERY_SUCCESS(2, "查询成功"), REMOVE_SUCCESS(3, "删除成功"),
    UPDATE_SUCCESS(4, "更新成功");

    private int state;
    private String stateInfo;

    private HeadLineEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static HeadLineEnum stateOf(int state) {
        for (HeadLineEnum headLineEnum : values()) {
            if (headLineEnum.getState() == state) {
                return headLineEnum;
            }
        }
        return null;
    }

}
