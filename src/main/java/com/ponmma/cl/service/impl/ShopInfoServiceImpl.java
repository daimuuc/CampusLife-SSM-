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
import com.ponmma.cl.util.PageCalculator;
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
    public ShopInfoExecution getShopInfoById(Integer id) throws ShopInfoException {
        ShopInfo shopInfo = null;
        try {
            shopInfo = shopInfoDao.queryShopInfoById(id);
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

    @Override
    @Transactional
    public ShopInfoExecution getShopInfoListCondition(ShopInfo shopCondition, int pageIndex, int pageSize) throws ShopInfoException {
        List<ShopInfo> shopInfoList = null;
        int cnt;
        //将页码转换成行码
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);

        try {
            //依据查询条件，调用dao层返回相关的店铺列表
            shopInfoList = shopInfoDao.queryShopInfoListCondition(shopCondition, rowIndex, pageSize);
            if (shopInfoList == null)
                throw new ShopInfoException("查询商铺信息失败");

            //依据相同的查询条件，返回店铺总数
            cnt = shopInfoDao.queryShopInfoCount(shopCondition);
        }catch (Exception e) {
            e.printStackTrace();
            throw new ShopInfoException("查询商铺信息失败");
        }

        ShopInfoExecution shopInfoExecution = new ShopInfoExecution(ShopInfoEnum.QUERY_SUCCESS, shopInfoList);
        shopInfoExecution.setCount(cnt);

        return shopInfoExecution;
    }

}
