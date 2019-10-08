package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.OrderInfo;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.ShopInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderInfoTest extends BaseTest {
    @Autowired
    private OrderInfoDao orderInfoDao;

    @Test
    public void  testInsertOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(8);
        orderInfo.setPersonInfo(personInfo);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(21);
        orderInfo.setProductInfo(productInfo);
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(9);
        orderInfo.setShopInfo(shopInfo);
        orderInfo.setStatus(0);
        orderInfo.setLastEditTime(new Date());

        int effectNum = orderInfoDao.insertOrderInfo(orderInfo);
        assertEquals(1, effectNum);
        System.out.println(orderInfo);
    }

    @Test
    public void testInsertOrderInfoBatch() {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OrderInfo orderInfo = new OrderInfo();
            PersonInfo personInfo = new PersonInfo();
            personInfo.setId(8);
            orderInfo.setPersonInfo(personInfo);
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(21);
            orderInfo.setProductInfo(productInfo);
            ShopInfo shopInfo = new ShopInfo();
            shopInfo.setId(9);
            orderInfo.setShopInfo(shopInfo);
            orderInfo.setStatus(0);
            orderInfo.setLastEditTime(new Date());
            orderInfoList.add(orderInfo);
        }

        int effectNum = orderInfoDao.insertOrderInfoBatch(orderInfoList);
        assertEquals(5, effectNum);
        for (OrderInfo orderInfo : orderInfoList)
            System.out.println(orderInfo);
    }

    @Test
    public void testQueryOrderInfoListCondition() {
        OrderInfo orderInfoCondition = new OrderInfo();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
        Date date = calendar.getTime();
        orderInfoCondition.setLastEditTime(date);
        List<OrderInfo> orderInfoList = orderInfoDao.queryOrderInfoListCondition(orderInfoCondition, 0, 5);
        System.out.println(date);
        System.out.println(orderInfoList.size());
        System.out.println(orderInfoList);
    }

    @Test
    public void testQueryOrderInfoCount() {
        OrderInfo orderInfoCondition = new OrderInfo();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
        Date date = calendar.getTime();
        orderInfoCondition.setLastEditTime(date);
        int cnt = orderInfoDao.queryOrderInfoCount(orderInfoCondition);
        System.out.println(cnt);
    }

    @Test
    public void testUpdateOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(17);
        orderInfo.setLastEditTime(new Date());
        orderInfo.setStatus(1);

        int effectNum = orderInfoDao.updateOrderInfo(orderInfo);
        assertEquals(1, effectNum);
    }
}
