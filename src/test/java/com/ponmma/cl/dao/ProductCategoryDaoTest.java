package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("港式奶茶");
        productCategory.setLastEditTime(new Date());
        productCategory.setShopInfoId(6);
        int effectNum = productCategoryDao.insertProductCategory(productCategory);
        assertEquals(1, effectNum);
        System.out.println(productCategory);
    }

    @Test
    public void testUpdateProductCategory() {
        Integer shopInfoId = 6;
        List<ProductCategory> productCategoryList =  productCategoryDao.queryProductCategoryByShopInfoId(shopInfoId);
        assertEquals(1, productCategoryList.size());
        ProductCategory productCategory = productCategoryList.get(0);
        System.out.println(productCategory);

        productCategory.setName("港式奶茶");
        int effectNum = productCategoryDao.updateProductCategory(productCategory);
        assertEquals(1, effectNum);
    }

    @Test
    public void testDeleteProductCategory() {
        Integer id = 1;
        int effectNum = productCategoryDao.deleteProductCategory(id);
        assertEquals(1, effectNum);
    }
}
