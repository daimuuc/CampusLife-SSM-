package com.ponmma.cl.dao;

import com.ponmma.cl.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoDao {

    /**
     * 插入订单信息
     * @param orderInfo
     * @return
     */
    int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 按批次插入订单信息
     * @param orderInfoList
     * @return
     */
    int insertOrderInfoBatch(@Param("orderInfoList")List<OrderInfo> orderInfoList);

    /**
     * 按条件分页查询订单信息
     * @param orderInfoCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<OrderInfo> queryOrderInfoListCondition(@Param("orderInfoCondition") OrderInfo orderInfoCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回条件查询订单信息个数
     * @param orderInfoCondition
     * @return
     */
    int queryOrderInfoCount(@Param("orderInfoCondition") OrderInfo orderInfoCondition);

    /**
     * 更新订单信息
     * @param orderInfo
     * @return
     */
    int updateOrderInfo(OrderInfo orderInfo);

}
