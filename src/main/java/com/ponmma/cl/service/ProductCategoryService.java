package com.ponmma.cl.service;

import com.ponmma.cl.dto.ProductCategoryExecution;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.exceptions.ProductCategoryException;

import java.util.List;

public interface ProductCategoryService {

    /**
     * 插入商品类别
     * @param productCategory
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution addProductCategory(ProductCategory productCategory) throws ProductCategoryException;

    /**
     * 批量插入商品类别
     * @param productCategoryList
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution addProductCategoryBatch(List<ProductCategory> productCategoryList) throws ProductCategoryException;

    /**
     * 获取商品类别
     * @param shopInfoId
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution getProductCategoryByShopInfoId(Integer shopInfoId) throws ProductCategoryException;

}
