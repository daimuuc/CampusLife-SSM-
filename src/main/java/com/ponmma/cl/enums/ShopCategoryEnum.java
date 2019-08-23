package com.ponmma.cl.enums;

public enum ShopCategoryEnum {
    IMAGE_EMPTY(-1, "商铺类别图片为空"), ADD_SUCCESS(1, "添加成功"),
    UPDATE_SUCCESS(2, "修改成功"), QUERY_SUCCESS(3, "查询成功");

    private int state;
    private String stateInfo;

    private ShopCategoryEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ShopCategoryEnum stateOf(int state) {
        for (ShopCategoryEnum areaEnum : values()) {
            if (areaEnum.getState() == state) {
                return areaEnum;
            }
        }
        return null;
    }

}
