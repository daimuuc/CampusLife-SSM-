package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.MutipleImageInfoDao;
import com.ponmma.cl.dao.ProductInfoDao;
import com.ponmma.cl.dao.SingleImageInfoDao;
import com.ponmma.cl.dto.ProductInfoExecution;
import com.ponmma.cl.entity.MutipleImageInfo;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import com.ponmma.cl.enums.ProductInfoEnum;
import com.ponmma.cl.exceptions.ProductInfoException;
import com.ponmma.cl.service.ProductInfoService;
import com.ponmma.cl.util.ImageHolder;
import com.ponmma.cl.util.ImageUtil;
import com.ponmma.cl.util.PageCalculator;
import com.ponmma.cl.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    private ProductInfoDao productInfoDao;
    @Autowired
    private MutipleImageInfoDao mutipleImageInfoDao;
    @Autowired
    private SingleImageInfoDao singleImageInfoDao;

    @Override
    @Transactional
    public ProductInfoExecution addProductInfo(ProductInfo productInfo, ImageHolder thumbnail,
                                               List<ImageHolder> productImgList) throws ProductInfoException{

        // 空值判断
        if (thumbnail == null || productImgList == null || productImgList.size() == 0) {
            return new ProductInfoExecution(ProductInfoEnum.IMAGE_EMPTY);
        }else {
            // 添加缩略图
            try {
                String path = addThumbnail(productInfo, thumbnail);
                SingleImageInfo singleImageInfo = new SingleImageInfo();
                singleImageInfo.setSrc(path);
                int effectNum = singleImageInfoDao.insertSingleImageInfo(singleImageInfo);
                if (effectNum <= 0)
                    throw new ProductInfoException("添加缩略图失败");
                productInfo.setSingleImageInfo(singleImageInfo);
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new ProductInfoException("添加缩略图失败");
            }

            // 添加商品信息
            try {
                productInfo.setLastEditTime(new Date());
                productInfo.setEnableStatus(1);
                int effectNum = productInfoDao.insertProductInfo(productInfo);
                if (effectNum <= 0)
                    throw new ProductInfoException("添加商品信息失败");
            }catch (Exception e) {
                e.printStackTrace();
                throw new ProductInfoException("添加商品信息失败");
            }

            // 添加详情图
            try {
                List<MutipleImageInfo> mutipleImageInfoList = addProductImgList(productInfo, productImgList);
                int effectNum = mutipleImageInfoDao.insertMutipleImageInfoBatch(mutipleImageInfoList);
                if (effectNum <= 0)
                    throw new ProductInfoException("添加详情图失败");
                productInfo.setMutipleImageInfoList(mutipleImageInfoList);
            }catch (Exception e) {
                e.printStackTrace();
                throw new ProductInfoException("添加详情图失败");
            }

            return new ProductInfoExecution(ProductInfoEnum.ADD_SUCCESS, productInfo);
        }

    }

    @Override
    @Transactional
    public ProductInfoExecution removeProductInfo(ProductInfo productInfo) throws ProductInfoException {
        try {
            // 删除详情图
            deleteProductImgList(productInfo.getId());

            // 删除商品详细
            int effectNum = productInfoDao.deleteProductInfo(productInfo.getId());
            if (effectNum <= 0)
                throw new ProductInfoException("删除商品信息失败");

            // 删除缩略图
            SingleImageInfo singleImageInfo = productInfo.getSingleImageInfo();
            String thumbnailAddr = singleImageInfo.getSrc();
            ImageUtil.deleteFileOrPath(thumbnailAddr);

            effectNum = singleImageInfoDao.deleteSingleImageInfo(singleImageInfo.getId());
            if (effectNum <= 0)
                throw new ProductInfoException("删除缩略图失败");

        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductInfoException(e.toString());
        }

        return new ProductInfoExecution(ProductInfoEnum.DELETE_SUCCESS, new ProductInfo());
    }

    @Override
    @Transactional
    public ProductInfoExecution modifyProductInfo(ProductInfo productInfo, ImageHolder thumbnail,
                                                  List<ImageHolder> productImgList) throws ProductInfoException{
        // 修改缩略图
        if (thumbnail != null) {
            try {
                // 删除原有图片
                SingleImageInfo singleImageInfo = productInfo.getSingleImageInfo();
                String thumbnailAddr = singleImageInfo.getSrc();
                ImageUtil.deleteFileOrPath(thumbnailAddr);

                // 更新缩略图
                thumbnailAddr = addThumbnail(productInfo, thumbnail);
                singleImageInfo.setSrc(thumbnailAddr);

                int effectNum = singleImageInfoDao.updateSingleImageInfo(singleImageInfo);
                if (effectNum <= 0)
                    throw new ProductInfoException("更新缩略图失败");
                productInfo.setSingleImageInfo(singleImageInfo);
            }catch (Exception e) {
                e.printStackTrace();
                throw new ProductInfoException(e.toString());
            }
        }

        // 修改详情图
        if (productImgList != null && productImgList.size() != 0) {
            try {
                // 删除原有图片
                deleteProductImgList(productInfo.getId());

                // 更新详情图
                List<MutipleImageInfo> mutipleImageInfoList = addProductImgList(productInfo, productImgList);
                int effectNum = mutipleImageInfoDao.insertMutipleImageInfoBatch(mutipleImageInfoList);
                if (effectNum <= 0)
                    throw new ProductInfoException("添加详情图失败");
                productInfo.setMutipleImageInfoList(mutipleImageInfoList);
            }catch (Exception e) {
                e.printStackTrace();
                throw new ProductInfoException(e.toString());
            }
        }

        // 修改商品信息
        try {
            productInfo.setLastEditTime(new Date());
            int effectNum = productInfoDao.updateProductInfo(productInfo);
            if (effectNum <= 0)
                throw  new ProductInfoException("修改商品信息失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductInfoException(e.toString());
        }

        return new ProductInfoExecution(ProductInfoEnum.UPDATE_SUCCESS, productInfo);
    }

    @Override
    @Transactional
    public ProductInfoExecution getProductInfo(ProductInfo productInfo) throws ProductInfoException {
        List<ProductInfo> productInfoList = null;
        // 查询商品信息
        try {
            productInfoList = productInfoDao.queryProductInfoList(productInfo);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductInfoException(e.toString());
        }

        return new ProductInfoExecution(ProductInfoEnum.QUERY_SUCCESS, productInfoList);
    }

    @Override
    @Transactional
    public  ProductInfoExecution getProductInfoListCondition(ProductInfo productCondition, int pageIndex, int pageSize) throws ProductInfoException {
        List<ProductInfo> productInfoList = null;
        int cnt;
        //将页码转换成行码
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);

        // 获取商品列表和总数
        try {
            productInfoList = productInfoDao.queryProductInfoListCondition(productCondition, rowIndex, pageSize);
            cnt = productInfoDao.queryProductInfoCount(productCondition);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ProductInfoException("获取商品列表和总数异常：" + e.toString());
        }

        ProductInfoExecution pie = new ProductInfoExecution(ProductInfoEnum.QUERY_SUCCESS, productInfoList);
        pie.setCount(cnt);

        return pie;
    }

    /**
     * 添加缩略图
     *
     * @param productInfo
     * @param thumbnail
     */
    private String addThumbnail(ProductInfo productInfo, ImageHolder thumbnail) {
        String dest = PathUtil.getUserImagePath(productInfo.getShopInfo().getId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        return thumbnailAddr;
    }

    /**
     * 批量添加图片
     *
     * @param productInfo
     * @param productImgHolderList
     */
    private List<MutipleImageInfo> addProductImgList(ProductInfo productInfo, List<ImageHolder> productImgHolderList) {
        // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest = PathUtil.getUserImagePath(productInfo.getShopInfo().getId());
        List<MutipleImageInfo> productImgList = new ArrayList<MutipleImageInfo>();
        // 遍历图片一次去处理，并添加进productImg实体类里
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            MutipleImageInfo productImg = new MutipleImageInfo();
            productImg.setSrc(imgAddr);
            productImg.setProductInfoId(productInfo.getId());
            productImgList.add(productImg);
        }

        return productImgList;
    }

    /**
     * 删除某个商品下的所有详情图
     *
     * @param productInfoId
     */
    private void deleteProductImgList(Integer productInfoId) {
        // 根据productInfoId获取原来的图片
        List<MutipleImageInfo> productImgList = mutipleImageInfoDao.queryMutipleImageInfoList(productInfoId);
        // 删除原来的图片
        for (MutipleImageInfo productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getSrc());
        }
        // 删除数据库里原有图片的信息
        mutipleImageInfoDao.deleteMutipleImageInfo(productInfoId);
    }

}
