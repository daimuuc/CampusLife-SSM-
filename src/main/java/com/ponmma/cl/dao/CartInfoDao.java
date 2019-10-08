package com.ponmma.cl.dao;

import com.ponmma.cl.entity.CartInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartInfoDao {

    /**
     * 插入购物车信息
     * @param cartInfo
     * @return
     */
    int insertCartInfo(CartInfo cartInfo);

    /**
     * 根据Id删除指定购物车信息
      * @param id
     * @return
     */
    int deleteCartInfoById(int id);

    /**
     * 根据personInfoId查询购物车信息列表
     * @param personInfoId
     * @return
     */
    List<CartInfo> queryCartInfoList(int personInfoId);

    /**
     * 根据productInfoId查询购物车信息列表
     * @param productInfoId
     * @return
     */
    CartInfo queryCartInfoByProductId(int productInfoId);

    /**
     * 批量删除购物车信息
     * @param cartInfoList
     * @return
     */
    int deleteCartInfoBatch(@Param("cartInfoList") List<CartInfo> cartInfoList);
}
