package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductInfoDaoTest extends BaseTest {
    @Autowired
    private ProductInfoDao productInfoDao;

    @Test
    public void testInsertProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("巴黎奶茶");
        productInfo.setLastEditTime(new Date());
        productInfo.setDes("浪漫巴黎，值得拥有");
        productInfo.setNormalPrice(20);
        productInfo.setPromotionPrice(16);
        productInfo.setPoint(20);
        productInfo.setEnableStatus(1);

        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(10);
        productInfo.setSingleImageInfo(singleImageInfo);

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(6);
        productInfo.setShopInfo(shopInfo);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(5);
        productInfo.setProductCategory(productCategory);

        int effectNum = productInfoDao.insertProductInfo(productInfo);
        assertEquals(1, effectNum);
        System.out.println(productInfo);
    }

    @Test
    public void testQueryProductInfoList() {
        ProductInfo productInfo = new ProductInfo();

//        productInfo.setName("巴黎");

//        ShopInfo shopInfo = new ShopInfo();
//        shopInfo.setId(6);
//        productInfo.setShopInfo(shopInfo);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(10);
        productInfo.setProductCategory(productCategory);

        List<ProductInfo> productInfoList = productInfoDao.queryProductInfoList(productInfo);
        System.out.println(productInfoList);
    }

    @Test
    public void testQueryProductInfoListCondition() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setEnableStatus(1);

//        productInfo.setName("奶");

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(6);
        productInfo.setShopInfo(shopInfo);

//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setId(2);
//        productInfo.setProductCategory(productCategory);

        List<ProductInfo> productInfoList = productInfoDao.queryProductInfoListCondition(productInfo, 1, 3);
        System.out.println(productInfoList);
        System.out.println(productInfoList.size());

        int size = productInfoDao.queryProductInfoCount(productInfo);
        System.out.println(size);
    }

    @Test
    public void testUpdateProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("巴黎");
        List<ProductInfo> productInfoList = productInfoDao.queryProductInfoList(productInfo);
        productInfo = productInfoList.get(0);
        productInfo.setName("埃菲尔奶茶");
        productInfo.setDes("埃菲尔，感受爱的味道");

        int effectNum = productInfoDao.updateProductInfo(productInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testDeleteProductInfo() {
        Integer id = 4;
        int effectNum = productInfoDao.deleteProductInfo(id);
        assertEquals(1, effectNum);
    }

}
