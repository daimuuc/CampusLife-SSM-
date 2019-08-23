package com.ponmma.cl.dao;

import com.ponmma.cl.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    /**
     * 插入商品类别
     * @param productCategory
     * @return
     */
    int insertProductCategory(ProductCategory productCategory);

    /**
     * 删除商品类别
     * @param id
     * @return
     */
    int deleteProductCategory(Integer id);

    /**
     * 更新商品类别
     * @param productCategory
     * @return
     */
    int updateProductCategory(ProductCategory productCategory);

    /**
     * 查询商品类别
     * @param shopInfoId
     * @return
     */
    List<ProductCategory> queryProductCategoryByShopInfoId(Integer shopInfoId);



}
