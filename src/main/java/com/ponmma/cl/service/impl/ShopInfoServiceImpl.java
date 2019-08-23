package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.ShopInfoDao;
import com.ponmma.cl.dao.SingleImageInfoDao;
import com.ponmma.cl.dto.ShopInfoExecution;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import com.ponmma.cl.enums.ShopInfoEnum;
import com.ponmma.cl.exceptions.ShopInfoException;
import com.ponmma.cl.service.ShopInfoService;
import com.ponmma.cl.util.ImageHolder;
import com.ponmma.cl.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopInfoServiceImpl implements ShopInfoService {
    @Autowired
    private ShopInfoDao shopInfoDao;
    @Autowired
    private SingleImageInfoDao singleImageInfoDao;

    @Override
    @Transactional
    public ShopInfoExecution addShopInfo(ShopInfo shopInfo) throws ShopInfoException {
        // 添加操作
        try {
            int effectNum = shopInfoDao.insertShopInfo(shopInfo);
            if (effectNum <= 0)
                throw new ShopInfoException("添加失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new ShopInfoException("添加失败");
        }

        return new ShopInfoExecution(ShopInfoEnum.ADD_SUCCESS, shopInfo);
    }

    @Override
    @Transactional
    public ShopInfoExecution modifyShopInfo(ShopInfo shopInfo) throws ShopInfoException {
        // 更新操作
        try {
            int effectNum = shopInfoDao.updateShopInfo(shopInfo);
            if (effectNum <= 0)
                throw new ShopInfoException("更新失败");
        }catch (Exception e) {
            e.printStackTrace();
            throw new ShopInfoException("更新失败");
        }

        return new ShopInfoExecution(ShopInfoEnum.UPDATE_SUCCESS, shopInfo);
    }

    @Override
    @Transactional
    public ShopInfoExecution getShopInfoByPersonInfoId(Integer personInfoId) throws ShopInfoException {
        ShopInfo shopInfo = null;
        try {
            shopInfo = shopInfoDao.queryShopInfoByPersonInfoId(personInfoId);
        }catch (Exception e) {
            e.printStackTrace();
            throw  new ShopInfoException("查询商铺信息失败");
        }

        return new ShopInfoExecution(ShopInfoEnum.QUERY_SUCCESS, shopInfo);
    }

    @Override
    @Transactional
    public ShopInfoExecution getShopInfoList() throws ShopInfoException {
        List<ShopInfo> shopInfoList = null;
        try {
            shopInfoList = shopInfoDao.queryShopInfoList();
        }catch (Exception e) {
            e.printStackTrace();
            throw new ShopInfoException("查询所有商铺信息失败");
        }

        return new ShopInfoExecution(ShopInfoEnum.QUERY_SUCCESS, shopInfoList);
    }

}
