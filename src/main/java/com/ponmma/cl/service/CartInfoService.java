package com.ponmma.cl.service;

import com.ponmma.cl.dto.CartInfoExecution;
import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.exceptions.CartInfoException;

public interface CartInfoService {

    /**
     * 插入购物车信息
     * @param cartInfo
     * @return
     * @throws CartInfoException
     */
    CartInfoExecution addCartInfo(CartInfo cartInfo) throws CartInfoException;

    /**
     * 根据Id删除指定购物车信息
     * @param id
     * @return
     * @throws CartInfoException
     */
    CartInfoExecution removeCartInfoById(int id) throws CartInfoException;

    /**
     * 根据personInfoId查询购物车信息列表
     * @param personInfoId
     * @return
     */
    CartInfoExecution getCartInfoList(int personInfoId) throws CartInfoException;

    /**
     * 根据productId查询购物车信息列表
     * @param productId
     * @return
     * @throws CartInfoException
     */
    CartInfoExecution getCartInfoByProductId(int productId) throws CartInfoException;

}
