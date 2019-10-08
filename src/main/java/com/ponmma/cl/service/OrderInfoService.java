package com.ponmma.cl.service;

import com.ponmma.cl.dto.OrderInfoExecution;
import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.entity.OrderInfo;
import com.ponmma.cl.exceptions.OrderInfoException;

import java.util.List;

public interface OrderInfoService {

    /**
     * 按批次插入订单信息
     * @param orderInfoList
     * @return
     * @throws OrderInfoException
     */
    OrderInfoExecution addOrderInfoBatch(List<OrderInfo> orderInfoList, List<CartInfo> cartInfoList) throws OrderInfoException;

    /**
     * 按条件查询订单信息列表
     * @param orderInfoCondition
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws OrderInfoException
     */
    OrderInfoExecution getOrderInfoListCondition(OrderInfo orderInfoCondition, int pageIndex, int pageSize) throws OrderInfoException;

    /**
     * 修改订单信息
     * @param orderInfo
     * @return
     * @throws OrderInfoException
     */
    OrderInfoExecution modifyOrderInfo(OrderInfo orderInfo) throws OrderInfoException;

}
