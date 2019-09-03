package com.ponmma.cl.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.cache.JedisUtil;
import com.ponmma.cl.dao.ShopCategoryDao;
import com.ponmma.cl.dao.SingleImageInfoDao;
import com.ponmma.cl.dto.ShopCategoryExecution;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.entity.SingleImageInfo;
import com.ponmma.cl.enums.ShopCategoryEnum;
import com.ponmma.cl.exceptions.ShopCategoryException;
import com.ponmma.cl.service.ShopCategoryService;
import com.ponmma.cl.util.ImageHolder;
import com.ponmma.cl.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private SingleImageInfoDao singleImageInfoDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Override
    @Transactional
    public ShopCategoryExecution addShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) throws ShopCategoryException {
        SingleImageInfo singleImageInfo;

        // 添加图片
        if (thumbnail != null) {
            try {
                // 上传图片，并获取相对地址
                String seperator = System.getProperty("file.separator");
                String dest = "/upload/images/shopCategory/";
                dest = dest.replace("/", seperator);
                String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
                // 添加单一图片信息
                singleImageInfo = new SingleImageInfo();
                singleImageInfo.setSrc(thumbnailAddr);
                int effectNum = singleImageInfoDao.insertSingleImageInfo(singleImageInfo);
                if (effectNum <= 0)
                    throw new ShopCategoryException("添加单一图片信息失败");
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new ShopCategoryException("添加单一图片信息失败");
            }
        }
        else
            return new ShopCategoryExecution(ShopCategoryEnum.IMAGE_EMPTY);

        // 添加商铺类别
        try {
            shopCategory.setSingleImageInfo(singleImageInfo);
            shopCategory.setLastEditTime(new Date());
            int effectNum = shopCategoryDao.insertShopCategory(shopCategory);
            if (effectNum <= 0)
                throw new ShopCategoryException("添加商铺类别失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new ShopCategoryException("添加商铺类别失败");
        }

        return new ShopCategoryExecution(ShopCategoryEnum.ADD_SUCCESS, shopCategory);
    }

    @Override
    @Transactional
    public ShopCategoryExecution modifyShopCategory(ShopCategory shopCategory, ImageHolder thumbnail) throws ShopCategoryException {
        //更新图片
        if (thumbnail != null) {
            try {
                // 删除图片
                SingleImageInfo singleImageInfo = shopCategory.getSingleImageInfo();
                String thumbnailAddr = singleImageInfo.getSrc();
                ImageUtil.deleteFileOrPath(thumbnailAddr);

                // 上传图片，并获取相对地址
                String seperator = System.getProperty("file.separator");
                String dest = "/upload/images/shopCategory/";
                dest = dest.replace("/", seperator);
                thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);

                // 更新单一图片信息
                singleImageInfo.setSrc(thumbnailAddr);
                int effectNum = singleImageInfoDao.updateSingleImageInfo(singleImageInfo);
                if (effectNum <= 0)
                    throw new ShopCategoryException("更新单一图片信息失败");
            }
            catch (Exception e) {
                e.printStackTrace();
                throw new ShopCategoryException("更新单一图片信息失败");
            }
        }

        //更新商铺类别
        try {
            shopCategory.setLastEditTime(new Date());
            int effectNum = shopCategoryDao.updateShopCategory(shopCategory);
            if (effectNum <= 0)
                throw new ShopCategoryException("更新商铺类别失败");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ShopCategoryException("更新商铺类别失败");
        }

        return new ShopCategoryExecution(ShopCategoryEnum.UPDATE_SUCCESS, shopCategory);
    }

    @Override
    @Transactional
    public ShopCategoryExecution getShopCategoryList() throws ShopCategoryException {
        // 定义redis的key
        String key = SHOPCATEGORYLISTKEY;
        // 定义接收对象
        List<ShopCategory> shopCategoryList = null;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();

        // 判断key是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库里面取出相应数据
            try {
                shopCategoryList = shopCategoryDao.queryShopCategoryList();
                if (shopCategoryList == null)
                    throw new ShopCategoryException("查询商铺类别失败");
            }catch (Exception e) {
                e.printStackTrace();
                throw new ShopCategoryException("查询商铺类别失败");
            }
            // 将相关的实体类集合转换成string,存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new ShopCategoryException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        }else {
            // 若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            // 指定要将string转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                // 将相关key对应的value里的的string转换成对象的实体类集合
                shopCategoryList = mapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                throw new ShopCategoryException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                throw new ShopCategoryException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                throw new ShopCategoryException(e.getMessage());
            }
        }

        return new ShopCategoryExecution(ShopCategoryEnum.QUERY_SUCCESS, shopCategoryList);
    }

    @Override
    @Transactional
    public ShopCategoryExecution getShopCategoryById(Integer id) throws ShopCategoryException {
        ShopCategory shopCategory = null;
        try {
            shopCategory = shopCategoryDao.queryShopCategoryById(id);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ShopCategoryException("查询商铺类别失败");
        }

        return new ShopCategoryExecution(ShopCategoryEnum.QUERY_SUCCESS, shopCategory);
    }
}
