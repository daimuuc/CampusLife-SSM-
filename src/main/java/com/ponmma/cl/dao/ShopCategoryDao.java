package com.ponmma.cl.dao;

import com.ponmma.cl.entity.ShopCategory;

import java.util.List;

public interface ShopCategoryDao {

    /**
     * 插入商铺类别
     * @param shopCategory
     * @return
     */
    int insertShopCategory(ShopCategory shopCategory);

    /**
     * 删除商铺类别
     * @param id
     * @return
     */
    int deleteShopCategory(Integer id);

    /**
     * 更新商铺类别
     * @param shopCategory
     * @return
     */
    int updateShopCategory(ShopCategory shopCategory);

    /**
     * 查询所有商铺类别
     * @return
     */
    List<ShopCategory> queryShopCategoryList();

    /**
     * 通过ID查询商铺类别
     * @param id
     * @return
     */
    ShopCategory queryShopCategoryById(Integer id);

}
