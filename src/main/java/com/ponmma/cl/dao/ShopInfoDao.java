package com.ponmma.cl.dao;

import com.ponmma.cl.entity.ShopInfo;

import java.util.List;

public interface ShopInfoDao {

    /**
     * 插入商铺信息
     * @param shopInfo
     * @return
     */
    int insertShopInfo(ShopInfo shopInfo);

    /**
     * 删除商铺信息
     * @param id
     * @return
     */
    int deleteShopInfo(Integer id);

    /**
     * 更新商铺信息
     * @param shopInfo
     * @return
     */
    int updateShopInfo(ShopInfo shopInfo);

    /**
     * 根据personInfoId查询商铺信息
     * @param personInfoId
     * @return
     */
    ShopInfo queryShopInfoByPersonInfoId(Integer personInfoId);

    /**
     * 查询所有商铺信息
     * @return
     */
    List<ShopInfo> queryShopInfoList();

}
