package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.ProductInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CartInfoDaoTest extends BaseTest {
    @Autowired
    private CartInfoDao cartInfoDao;

    @Test
    public void testInsertCartInfo() {
        CartInfo cartInfo = new CartInfo();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(11);
        cartInfo.setPersonInfo(personInfo);
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(15);
        cartInfo.setProductInfo(productInfo);

        int effectNum = cartInfoDao.insertCartInfo(cartInfo);
        assertEquals(1, effectNum);
        System.out.println(cartInfo);
    }

    @Test
    public void testQueryCartInfoList() {
        int personInfoId = 11;
        List<CartInfo> cartInfoList = cartInfoDao.queryCartInfoList(personInfoId);
        assertEquals(1, cartInfoList.size());
        System.out.println(cartInfoList.get(0));
    }

    @Test
    public void testQueryCartInfoByProductId() {
        int productInfoId = 16;
        CartInfo cartInfo = cartInfoDao.queryCartInfoByProductId(productInfoId);
        System.out.println(cartInfo);
    }

    @Test
    public void testDeleteCartInfoById() {
        int id = 1;
        int effectNum = cartInfoDao.deleteCartInfoById(id);
        assertEquals(1, effectNum);
    }

    @Test
    public void testDeleteCartInfoBatch() {
        List<CartInfo> cartInfoList = new ArrayList<>();
        CartInfo cartInfo = new CartInfo();
        cartInfo.setId(1);
        cartInfoList.add(cartInfo);
        cartInfo = new CartInfo();
        cartInfo.setId(2);
        cartInfoList.add(cartInfo);

        int effectNum = cartInfoDao.deleteCartInfoBatch(cartInfoList);
        assertEquals(2, effectNum);
    }
}
