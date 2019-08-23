package com.ponmma.cl.service;

import com.ponmma.cl.dto.ShopCategoryExecution;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.exceptions.ShopCategoryException;
import com.ponmma.cl.util.ImageHolder;

public interface ShopCategoryService {

    /**
     * 添加商铺类别
     * @param shopCategory
     * @param thumbnail
     * @return
     */
    ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) throws ShopCategoryException;

    /**
     * 更新商铺类别
     * @param shopCategory
     * @param thumbnail
     * @return
     */
    ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) throws ShopCategoryException;

    /**
     * 查询所有商铺类别
     * @return
     */
    ShopCategoryExecution getShopCategoryList() throws ShopCategoryException;

    /**
     * 查询商铺类别
     * @param id
     * @return
     * @throws ShopCategoryException
     */
    ShopCategoryExecution getShopCategoryById(Integer id) throws ShopCategoryException;

}
