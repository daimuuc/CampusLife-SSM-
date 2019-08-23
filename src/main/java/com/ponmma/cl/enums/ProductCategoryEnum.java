package com.ponmma.cl.enums;

public enum ProductCategoryEnum {

    ADD_SUCCESS(1, "添加成功"), QUERY_SUCCESS(2, "查询成功");

    private int state;
    private String stateInfo;

    private ProductCategoryEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ProductCategoryEnum stateOf(int state) {
        for (ProductCategoryEnum productCategoryEnum : values()) {
            if (productCategoryEnum.getState() == state) {
                return productCategoryEnum;
            }
        }
        return null;
    }

}
