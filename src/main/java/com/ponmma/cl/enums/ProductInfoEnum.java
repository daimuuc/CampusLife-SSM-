package com.ponmma.cl.enums;

public enum ProductInfoEnum {

    IMAGE_EMPTY(-1, "缩略图或详情图不能为空"), ADD_SUCCESS(1, "添加成功"), QUERY_SUCCESS(2, "查询成功"),
    DELETE_SUCCESS(3, "删除成功"), UPDATE_SUCCESS(4, "更新成功");

    private int state;
    private String stateInfo;

    private ProductInfoEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductInfoEnum stateOf(int state) {
        for (ProductInfoEnum productInfoEnum : values()) {
            if (productInfoEnum.getState() == state) {
                return productInfoEnum;
            }
        }
        return null;
    }

}
