package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.entity.SingleImageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testInsertShopCategory() {
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setName("test");
        shopCategory.setLastEditTime(new Date());
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(5);
        shopCategory.setSingleImageInfo(singleImageInfo);
        int effectNum = shopCategoryDao.insertShopCategory(shopCategory);
        assertEquals(1, effectNum);
        System.out.println(shopCategory.getId());
    }

    @Test
    public void testQueryShopCategoryList() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategoryList();
        assertEquals(1, shopCategoryList.size());
        System.out.println(shopCategoryList.get(0));
    }

    @Test
    public void testUpdateShopCategory() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategoryList();
        ShopCategory shopCategory = shopCategoryList.get(0);
        shopCategory.setName("test2");
        int effctNum = shopCategoryDao.updateShopCategory(shopCategory);
        assertEquals(1, effctNum);
    }

    @Test
    public void testDeleteShopCategory() {
        Integer id = 1;
        int effectNum = shopCategoryDao.deleteShopCategory(1);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryShopCategoryById() {
        Integer id = 3;
        ShopCategory shopCategory = shopCategoryDao.queryShopCategoryById(id);
        if (shopCategory != null)
            System.out.println(shopCategory);
    }
}
