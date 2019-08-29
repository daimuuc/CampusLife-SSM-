package com.ponmma.cl.dao;

import com.ponmma.cl.entity.ProductInfo;

import java.util.List;

public interface ProductInfoDao {

    /**
     * 插入商品信息
     * @param productInfo
     * @return
     */
    int insertProductInfo(ProductInfo productInfo);

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    int deleteProductInfo(Integer id);

    /**
     * 更新商品信息
     * @param productInfo
     * @return
     */
    int updateProductInfo(ProductInfo productInfo);

    /**
     * 组合查询
     * @param productInfo
     * @return
     */
    List<ProductInfo> queryProductInfoList(ProductInfo productInfo);

}
