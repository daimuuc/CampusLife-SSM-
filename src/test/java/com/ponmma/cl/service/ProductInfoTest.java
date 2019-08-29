package com.ponmma.cl.service;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.dto.ProductInfoExecution;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import com.ponmma.cl.enums.ProductInfoEnum;
import com.ponmma.cl.util.ImageHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductInfoTest extends BaseTest {
    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void testAddProductInfo() throws IOException {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("柏林奶茶");
        productInfo.setLastEditTime(new Date());
        productInfo.setDes("柏林，带给你爱的味道");
        productInfo.setNormalPrice(20);
        productInfo.setPromotionPrice(16);
        productInfo.setPoint(20);
        productInfo.setEnableStatus(1);

        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(6);
        productInfo.setShopInfo(shopInfo);

        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(5);
        productInfo.setProductCategory(productCategory);

        File file = new File("/Users/antony/Desktop/license.jpg");
        ImageHolder thumbnail = new ImageHolder(file.getName(), new FileInputStream(file));

        List<ImageHolder> productImgList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            File file1 = new File("/Users/antony/Desktop/license.jpg");
            ImageHolder thumbnail1 = new ImageHolder(file1.getName(), new FileInputStream(file1));
            productImgList.add(thumbnail1);
        }

        ProductInfoExecution pe = productInfoService.addProductInfo(productInfo, thumbnail, productImgList);
        if (pe.getState() == ProductInfoEnum.ADD_SUCCESS.getState())
            System.out.println(pe.getProductInfo());

    }

    @Test
    public void testGetProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("柏林");
//        ShopInfo shopInfo = new ShopInfo();
//        shopInfo.setId(5);
//        productInfo.setShopInfo(shopInfo);
        ProductInfoExecution pe = productInfoService.getProductInfo(productInfo);
        if (pe.getState() == ProductInfoEnum.QUERY_SUCCESS.getState())
            System.out.println(pe.getProductInfoList());
    }

    @Test
    public void testModifyProductInfo() throws IOException{
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName("柏林");
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(6);
        productInfo.setShopInfo(shopInfo);
        ProductInfoExecution pe = productInfoService.getProductInfo(productInfo);
        productInfo = pe.getProductInfoList().get(0);
        productInfo.setShopInfo(shopInfo);

        productInfo.setName("埃菲尔奶茶");
        productInfo.setDes("埃菲尔，见证爱情");

        File file = new File("/Users/antony/Desktop/profile.jpg");
        ImageHolder thumbnail = new ImageHolder(file.getName(), new FileInputStream(file));

        List<ImageHolder> productImgList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            File file1 = new File("/Users/antony/Desktop/profile.jpg");
            ImageHolder thumbnail1 = new ImageHolder(file1.getName(), new FileInputStream(file1));
            productImgList.add(thumbnail1);
        }
        pe = productInfoService.modifyProductInfo(productInfo, thumbnail, productImgList);
        if (pe.getState() == ProductInfoEnum.UPDATE_SUCCESS.getState())
            System.out.println(pe.getProductInfo());
    }

    @Test
    public void testRemoveProductInfo() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(12);
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(27);
        singleImageInfo.setSrc("/upload/images/6/2019082716403459078.jpg");
        productInfo.setSingleImageInfo(singleImageInfo);

        ProductInfoExecution pe = productInfoService.removeProductInfo(productInfo);
        if (pe.getState() == ProductInfoEnum.DELETE_SUCCESS.getState())
            System.out.println(pe.getProductInfo());
    }
}
