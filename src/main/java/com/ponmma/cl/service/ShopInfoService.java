package com.ponmma.cl.service;

import com.ponmma.cl.dto.ShopInfoExecution;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.exceptions.ShopInfoException;
import com.ponmma.cl.util.ImageHolder;

public interface ShopInfoService {

    /**
     * 插入商铺信息
     * @param shopInfo
     * @return
     * @throws ShopInfoException
     */
    ShopInfoExecution addShopInfo(ShopInfo shopInfo) throws ShopInfoException;

    /**
     * 修改商铺信息
     * @param shopInfo
     * @return
     * @throws ShopInfoException
     */
    ShopInfoExecution modifyShopInfo(ShopInfo shopInfo) throws ShopInfoException;

    /**
     * 查询商铺信息
     * @param personInfoId
     * @return
     * @throws ShopInfoException
     */
    ShopInfoExecution getShopInfoByPersonInfoId(Integer personInfoId) throws ShopInfoException;

    /**
     * 查询所有商铺信息
     * @return
     * @throws ShopInfoException
     */
    ShopInfoExecution getShopInfoList() throws ShopInfoException;

}
