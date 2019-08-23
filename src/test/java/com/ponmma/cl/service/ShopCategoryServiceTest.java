package com.ponmma.cl.service;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.dto.ShopCategoryExecution;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.enums.ShopCategoryEnum;
import com.ponmma.cl.util.ImageHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class ShopCategoryServiceTest extends BaseTest {
    @Autowired
    ShopCategoryService shopCategoryService;

    @Test
    public void testAddShopCategory() throws Exception{
        File file = new File("/Users/antony/Desktop/profile.jpg");
        ImageHolder imageHolder = new ImageHolder(file.getName(), new FileInputStream(file));
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setName("test");
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.addShopCategory(shopCategory, imageHolder);
        if (shopCategoryExecution.getState() == ShopCategoryEnum.ADD_SUCCESS.getState())
            System.out.println(shopCategoryExecution.getShopCategory());
        else
            System.out.println(shopCategoryExecution.getStateInfo());
    }

    @Test
    public void testModifyShopCategory() throws Exception{
        ShopCategoryExecution shopCategoryExecution = shopCategoryService.getShopCategoryList();
        List<ShopCategory> shopCategoryList = shopCategoryExecution.getShopCategoryList();
        ShopCategory shopCategory = shopCategoryList.get(0);
        shopCategory.setName("test2");
        shopCategoryExecution = shopCategoryService.modifyShopCategory(shopCategory, null);
        if (shopCategoryExecution.getState() == ShopCategoryEnum.UPDATE_SUCCESS.getState())
            System.out.println(shopCategoryExecution.getShopCategory());
        else
            System.out.println(shopCategoryExecution.getStateInfo());

    }
}
