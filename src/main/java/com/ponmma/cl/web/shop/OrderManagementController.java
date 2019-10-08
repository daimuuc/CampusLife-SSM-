package com.ponmma.cl.web.shop;

import com.ponmma.cl.dto.OrderInfoExecution;
import com.ponmma.cl.entity.OrderInfo;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.OrderInfoEnum;
import com.ponmma.cl.service.OrderInfoService;
import com.ponmma.cl.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "shop")
public class OrderManagementController {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 根据组合条件分页查询订单信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = "getorderlist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getOrderList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        /*获取相关参数信息*/
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        int timeId = HttpServletRequestUtil.getInt(request, "timeId");
        int orderCategoryId = HttpServletRequestUtil.getInt(request, "orderCategoryId");
        String productName = HttpServletRequestUtil.getString(request, "productName");

        /*配置组合查询条件*/
        OrderInfo orderInfoCondition = new OrderInfo();
        // 设置查询开始时间
        if (timeId != 0) {
            Calendar calendar = Calendar.getInstance();
            switch (timeId) {
                case 1:
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
                    break;
                case 2:
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 3);
                    break;
                case 3:
                    calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 6);
                    break;
            }
            orderInfoCondition.setLastEditTime(calendar.getTime());
        }
        // 设置订单状态
        if (orderCategoryId != -1)
            orderInfoCondition.setStatus(orderCategoryId);
        // 设置模糊查询产品名字
        if (productName != null) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setName(productName);
            orderInfoCondition.setProductInfo(productInfo);
        }
        // 设置店铺ID
        ShopInfo shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
        orderInfoCondition.setShopInfo(shopInfo);

        /*组合查询*/
        try {
            OrderInfoExecution oie = orderInfoService.getOrderInfoListCondition(orderInfoCondition, pageIndex, pageSize);
            if (oie.getState() == OrderInfoEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("count", oie.getCount());
                modelMap.put("orderInfoList", oie.getOrderInfoList());
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

    /**
     * 修改对应订单的状态
     * @param orderId
     * @return
     */
    @RequestMapping(value = "updateorderstatus", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> updateOrderStatus(@RequestParam("orderId") int orderId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        /*订单信息*/
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setStatus(1);

        /*修改订单状态*/
        try {
            OrderInfoExecution oie = orderInfoService.modifyOrderInfo(orderInfo);
            if (oie.getState() == OrderInfoEnum.UPDATE_SUCCESS.getState()) {
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
