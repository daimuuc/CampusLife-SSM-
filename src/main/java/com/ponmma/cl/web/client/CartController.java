package com.ponmma.cl.web.client;

import com.ponmma.cl.dto.CartInfoExecution;
import com.ponmma.cl.dto.OrderInfoExecution;
import com.ponmma.cl.entity.CartInfo;
import com.ponmma.cl.entity.OrderInfo;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.enums.CartInfoEnum;
import com.ponmma.cl.enums.OrderInfoEnum;
import com.ponmma.cl.service.CartInfoService;
import com.ponmma.cl.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping(value = "client")
public class CartController {
    @Autowired
    private CartInfoService cartInfoService;
    @Autowired
    private OrderInfoService orderInfoService;

    @RequestMapping(value = "getcartinfolist")
    @ResponseBody
    private Map<String, Object> getCartInfoList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        // 获取购物车信息列表
        try {
            // 获取用户信息
            PersonInfo personInfo = (PersonInfo)request.getSession().getAttribute("personInfo");

            CartInfoExecution cie = cartInfoService.getCartInfoList(personInfo.getId());
            if (cie.getState() == CartInfoEnum.QUERY_SUCCESS.getState()) {
                List<CartInfo> cartInfoList = cie.getCartInfoList();

                // 根据ShopId分组
                Map<Integer, List<CartInfo>> map = new HashMap<Integer, List<CartInfo>>();
                for (CartInfo cartInfo : cartInfoList) {
                    int shopId = cartInfo.getProductInfo().getShopInfo().getId();
                    if (map.containsKey(shopId)) {
                        List<CartInfo> cartInfos = map.get(shopId);
                        cartInfos.add(cartInfo);
                        map.put(shopId, cartInfos);
                    }else {
                        List<CartInfo> cartInfos = new ArrayList<>();
                        cartInfos.add(cartInfo);
                        map.put(shopId, cartInfos);
                    }
                }

                modelMap.put("success", true);
                modelMap.put("cartInfoListMap", map);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", cie.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "deletecartinfo")
    @ResponseBody
    private Map<String, Object> deleteCartInfo(@RequestParam("cartId")int cartId) {
        Map<String, Object> modelMap = new HashMap<>();

        // 根据Id删除购物车信息
        try {
            CartInfoExecution cie = cartInfoService.removeCartInfoById(cartId);
            if (cie.getState() == CartInfoEnum.DELETE_SUCCESS.getState()) {
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", cie.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "addorderinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> addOrderInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();

        // 获取用户信息
        PersonInfo personInfo = (PersonInfo)request.getSession().getAttribute("personInfo");

        // 获取购物车信息列表
        List<CartInfo> cartInfoList = null;
        try {
            CartInfoExecution cie = cartInfoService.getCartInfoList(personInfo.getId());
            if (cie.getState() == CartInfoEnum.QUERY_SUCCESS.getState()) {
                cartInfoList = cie.getCartInfoList();
                if (cartInfoList.size() == 0) {
                    modelMap.put("success", true);
                    return modelMap;
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", cie.getStateInfo());
                return modelMap;
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 按批插入订单信息
        List<OrderInfo> orderInfoList = new ArrayList<>();
        for (CartInfo cartInfo : cartInfoList) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setPersonInfo(personInfo);
            orderInfo.setProductInfo(cartInfo.getProductInfo());
            orderInfo.setShopInfo(cartInfo.getProductInfo().getShopInfo());
            orderInfo.setStatus(0);
            orderInfo.setLastEditTime(new Date());
            orderInfoList.add(orderInfo);
        }
        try {
            OrderInfoExecution oie = orderInfoService.addOrderInfoBatch(orderInfoList, cartInfoList);
            if (oie.getState() == OrderInfoEnum.ADD_SUCCESS.getState()) {
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", oie.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }


        return modelMap;
    }
}
