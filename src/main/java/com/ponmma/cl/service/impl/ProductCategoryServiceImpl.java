package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.ProductCategoryDao;
import com.ponmma.cl.dto.ProductCategoryExecution;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.enums.ProductCategoryEnum;
import com.ponmma.cl.exceptions.ProductCategoryException;
import com.ponmma.cl.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    @Transactional
    public ProductCategoryExecution addProductCategory(ProductCategory productCategory) throws ProductCategoryException {
        try {
            productCategory.setLastEditTime(new Date());
           int effectNum = productCategoryDao.insertProductCategory(productCategory);
           if (effectNum <= 0)
               throw new ProductCategoryException("插入商品类别失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductCategoryException("插入商品类别失败");
        }

        return new ProductCategoryExecution(ProductCategoryEnum.ADD_SUCCESS, productCategory);
    }

    @Override
    @Transactional
    public ProductCategoryExecution addProductCategoryBatch(List<ProductCategory> productCategoryList) throws ProductCategoryException {
        try {
            for (ProductCategory productCategory : productCategoryList) {
                productCategory.setLastEditTime(new Date());
                int effectNum = productCategoryDao.insertProductCategory(productCategory);
                if (effectNum <= 0)
                    throw new ProductCategoryException("插入商品类别失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductCategoryException(e.toString());
        }

        return new ProductCategoryExecution(ProductCategoryEnum.ADD_SUCCESS, productCategoryList);
    }

    @Override
    @Transactional
    public ProductCategoryExecution getProductCategoryByShopInfoId(Integer shopInfoId) throws ProductCategoryException {
        List<ProductCategory> productCategoryList = null;
        try {
            productCategoryList = productCategoryDao.queryProductCategoryByShopInfoId(shopInfoId);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductCategoryException("查询商品类别失败");
        }

        return new ProductCategoryExecution(ProductCategoryEnum.QUERY_SUCCESS, productCategoryList);
    }

}
