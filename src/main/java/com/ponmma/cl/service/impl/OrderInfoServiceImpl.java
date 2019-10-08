package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.CartInfoDao;
import com.ponmma.cl.dao.OrderInfoDao;
import com.ponmma.cl.dto.OrderInfoExecution;
import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.entity.OrderInfo;
import com.ponmma.cl.enums.OrderInfoEnum;
import com.ponmma.cl.exceptions.OrderInfoException;
import com.ponmma.cl.service.OrderInfoService;
import com.ponmma.cl.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private CartInfoDao cartInfoDao;

    @Override
    @Transactional
    public OrderInfoExecution addOrderInfoBatch(List<OrderInfo> orderInfoList, List<CartInfo> cartInfoList) throws OrderInfoException {
        // 批量添加订单信息
        try {
            int effectNum = orderInfoDao.insertOrderInfoBatch(orderInfoList);
            if (effectNum <= 0) {
                OrderInfoExecution oie = new OrderInfoExecution(OrderInfoEnum.ADD_FAILURE);
                return oie;
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new OrderInfoException("批量添加订单信息：" + e.toString());
        }

        // 批量删除购物车信息
        try {
            int effectNum = cartInfoDao.deleteCartInfoBatch(cartInfoList);
            if (effectNum <= 0)
                throw new OrderInfoException("批量添加订单信息：批量删除购物车信息失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new OrderInfoException("批量添加订单信息：" + e.toString());
        }

        return new OrderInfoExecution(OrderInfoEnum.ADD_SUCCESS, orderInfoList);
    }

    @Override
    @Transactional
    public OrderInfoExecution getOrderInfoListCondition(OrderInfo orderInfoCondition, int pageIndex, int pageSize) throws OrderInfoException {
        // 查询所得订单信息列表
        List<OrderInfo> orderInfoList = null;
        // 满足该查询条件订单信息个数
        int cnt = -1;
        //将页码转换成行码
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);

        // 查询订单信息
        try {
            orderInfoList = orderInfoDao.queryOrderInfoListCondition(orderInfoCondition, rowIndex, pageSize);
            cnt = orderInfoDao.queryOrderInfoCount(orderInfoCondition);
        }catch (Exception e) {
            e.printStackTrace();
            throw new OrderInfoException("组合查询订单信息列表失败：" + e.toString());
        }

        OrderInfoExecution orderInfoExecution = new OrderInfoExecution(OrderInfoEnum.QUERY_SUCCESS, orderInfoList);
        orderInfoExecution.setCount(cnt);
        return orderInfoExecution;
    }

    @Override
    @Transactional
    public OrderInfoExecution modifyOrderInfo(OrderInfo orderInfo) throws OrderInfoException {
        // 更新时间
        orderInfo.setLastEditTime(new Date());

        // 修改订单信息
        try {
            int effectNum = orderInfoDao.updateOrderInfo(orderInfo);
            if (effectNum <= 0) {
                return new OrderInfoExecution(OrderInfoEnum.UPDATE_FAILURE);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new OrderInfoException("修改订单信息出错：" + e.toString());
        }

        return new OrderInfoExecution(OrderInfoEnum.UPDATE_SUCCESS, orderInfo);
    }

}
