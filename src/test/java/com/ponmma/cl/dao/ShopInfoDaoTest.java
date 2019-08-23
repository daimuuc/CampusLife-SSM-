package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.Area;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.entity.ShopInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
public class ShopInfoDaoTest extends BaseTest {
    @Autowired
    private ShopInfoDao shopInfoDao;

    @Test
    public void testInsertShopInfo() {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setAddr("武汉大学南苑");
        shopInfo.setDes("静静奶茶店，甄选优品");
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(9);
        shopInfo.setPersonInfo(personInfo);
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setId(6);
        shopInfo.setShopCategory(shopCategory);
        Area area = new Area();
        area.setId(10);
        shopInfo.setArea(area);

        int effectNum = shopInfoDao.insertShopInfo(shopInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryShopInfoByPersonInfoId() {
        Integer personInfoId = 9;
        ShopInfo shopInfo = shopInfoDao.queryShopInfoByPersonInfoId(personInfoId);
        System.out.println(shopInfo);
    }

    @Test
    public void testQueryShopInfoList() {
        List<ShopInfo> shopInfoList = shopInfoDao.queryShopInfoList();
        assertEquals(1, shopInfoList.size());
    }

    @Test
    public void testUpdateShopInfo() {
        Integer personInfoId = 9;
        ShopInfo shopInfo = shopInfoDao.queryShopInfoByPersonInfoId(personInfoId);
        shopInfo.setAddr("四川大学南苑");
        int effectNum = shopInfoDao.updateShopInfo(shopInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testDeleteShopInfo() {
        Integer id = 1;
        int effectNum = shopInfoDao.deleteShopInfo(id);
        assertEquals(1, effectNum);
    }
}
