package com.ponmma.cl.dao;

import com.ponmma.cl.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoDao {

    /**
     * 插入商品信息
     * @param productInfo
     * @return
     */
    int insertProductInfo(ProductInfo productInfo);

    /**
     * 删除商品信息
     * @param id
     * @return
     */
    int deleteProductInfo(Integer id);

    /**
     * 更新商品信息
     * @param productInfo
     * @return
     */
    int updateProductInfo(ProductInfo productInfo);

    /**
     * 组合查询
     * @param productInfo
     * @return
     */
    List<ProductInfo> queryProductInfoList(ProductInfo productInfo);

    /**
     * 按照条件分页查询商品信息
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<ProductInfo> queryProductInfoListCondition(@Param("productCondition") ProductInfo productCondition, @Param("rowIndex") int rowIndex,
                                                    @Param("pageSize") int pageSize);

    /**
     * 返回条件查询商品信息个数
     * @param productCondition
     * @return
     */
    int queryProductInfoCount(ProductInfo productCondition);

}
