package com.ponmma.cl.service;

import com.ponmma.cl.dto.ProductInfoExecution;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.exceptions.ProductInfoException;
import com.ponmma.cl.util.ImageHolder;

import java.util.List;

public interface ProductInfoService {

    /**
     * 添加商品信息
     *
     * @param productInfo
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductInfoException
     */
    ProductInfoExecution addProductInfo(ProductInfo productInfo, ImageHolder thumbnail,
                                        List<ImageHolder> productImgList) throws ProductInfoException;

    /**
     * 删除商品信息
     * @param productInfo
     * @return
     * @throws ProductInfoException
     */
    ProductInfoExecution removeProductInfo(ProductInfo productInfo) throws ProductInfoException;

    /**
     * 修改商品信息
     *
     * @param productInfo
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductInfoException
     */
    ProductInfoExecution modifyProductInfo(ProductInfo productInfo, ImageHolder thumbnail,
                                           List<ImageHolder> productImgList) throws ProductInfoException;


    /**
     * 查询商品信息
     *
     * @param productInfo
     * @return
     * @throws ProductInfoException
     */
    ProductInfoExecution getProductInfo(ProductInfo productInfo) throws ProductInfoException;

    /**
     * 按照条件分页查询商品信息
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws ProductInfoException
     */
    ProductInfoExecution getProductInfoListCondition(ProductInfo productCondition, int pageIndex, int pageSize) throws ProductInfoException;

}
