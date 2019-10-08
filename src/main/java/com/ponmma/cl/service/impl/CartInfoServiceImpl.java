package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.CartInfoDao;
import com.ponmma.cl.dto.CartInfoExecution;
import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.enums.CartInfoEnum;
import com.ponmma.cl.exceptions.CartInfoException;
import com.ponmma.cl.service.CartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartInfoServiceImpl implements CartInfoService {
    @Autowired
    private CartInfoDao cartInfoDao;

    @Override
    @Transactional
    public CartInfoExecution addCartInfo(CartInfo cartInfo) throws CartInfoException {
        // 判断购物车信息是否完整
        if (cartInfo == null || cartInfo.getPersonInfo() == null || cartInfo.getPersonInfo().getId() == null ||
                cartInfo.getProductInfo() == null || cartInfo.getProductInfo().getId() == null)
            return new CartInfoExecution(CartInfoEnum.ADD_FAILURE);

        // 添加购物车信息
        try {
            int effectNum = cartInfoDao.insertCartInfo(cartInfo);
            if (effectNum <= 0)
                throw new CartInfoException("添加购物车信息失败");
        }catch (Exception e) {
            throw new CartInfoException(e.toString());
        }

        return new CartInfoExecution(CartInfoEnum.ADD_SUCCESS, cartInfo);
    }

    @Override
    @Transactional
    public CartInfoExecution removeCartInfoById(int id) throws CartInfoException {
        // 删除购物车信息
        try {
            int effectNum = cartInfoDao.deleteCartInfoById(id);
            if (effectNum <= 0)
                throw new CartInfoException("删除购物车信息失败");
        }catch (Exception e) {
            throw new CartInfoException(e.toString());
        }

        return new CartInfoExecution(CartInfoEnum.DELETE_SUCCESS, new CartInfo());
    }

    @Override
    @Transactional
    public CartInfoExecution getCartInfoList(int personInfoId) throws CartInfoException {
        List<CartInfo> cartInfoList = null;

        // 查询购物车信息
        try {
           cartInfoList = cartInfoDao.queryCartInfoList(personInfoId);
        }catch (Exception e) {
            throw new CartInfoException(e.toString());
        }

        return new CartInfoExecution(CartInfoEnum.QUERY_SUCCESS, cartInfoList);
    }

    @Override
    @Transactional
    public CartInfoExecution getCartInfoByProductId(int productId) throws CartInfoException {
        CartInfo cartInfo = null;

        // 查询购物车信息
        try {
            cartInfo = cartInfoDao.queryCartInfoByProductId(productId);
        }catch (Exception e) {
            throw new CartInfoException(e.toString());
        }

        return new CartInfoExecution(CartInfoEnum.QUERY_SUCCESS, cartInfo);
    }

}
