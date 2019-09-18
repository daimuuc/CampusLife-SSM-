package com.ponmma.cl.web.client;

import com.ponmma.cl.dto.ProductCategoryExecution;
import com.ponmma.cl.dto.ProductInfoExecution;
import com.ponmma.cl.dto.ShopInfoExecution;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.ProductCategoryEnum;
import com.ponmma.cl.enums.ProductInfoEnum;
import com.ponmma.cl.enums.ShopInfoEnum;
import com.ponmma.cl.service.ProductCategoryService;
import com.ponmma.cl.service.ProductInfoService;
import com.ponmma.cl.service.ShopInfoService;
import com.ponmma.cl.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "client")
public class ShopDetailController {
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping(value = "shopinfoandproductcategory", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopinfoAndProductcategory(HttpServletRequest request, @RequestParam("shopId")int shopId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取商铺信息
        try {
            ShopInfoExecution se = shopInfoService.getShopInfoById(shopId);
            if (se.getState() == ShopInfoEnum.QUERY_SUCCESS.getState()) {
                ShopInfo shopInfo = se.getShopInfo();
                if (shopInfo != null) {
                    modelMap.put("success", true);
                    modelMap.put("shopInfo", shopInfo);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "店铺不存在");
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        // 获取商铺信息失败则返回
        if ((boolean)modelMap.get("success") == false)
            return modelMap;

        // 获取商品类别
        try {
            ProductCategoryExecution pce = productCategoryService.getProductCategoryByShopInfoId(shopId);
            if (pce.getState() == ProductCategoryEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("productCategoryList", pce.getProductCategoryList());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pce.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "productList", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取商店id
        int shopId = HttpServletRequestUtil.getInt(request, "shopId");
        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取一页需要显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");

        // 非空判断
        if (shopId > -1 && pageIndex > -1 && pageSize > -1) {
            // 试着获取产品类别id
            int productCategoryId = HttpServletRequestUtil.getInt(request, "productCategoryId");
            // 试着获取模糊查询的名字
            String name = HttpServletRequestUtil.getString(request, "productName");

            // 获取组合之后的查询条件
            ProductInfo productCondition = compactProductConditionSearch(shopId, productCategoryId, name);

            // 获取产品列表和总数
            try {
                ProductInfoExecution pie = productInfoService.getProductInfoListCondition(productCondition, pageIndex, pageSize);
                if(pie.getState() == ProductInfoEnum.QUERY_SUCCESS.getState()) {
                    modelMap.put("success", true);
                    modelMap.put("productList", pie.getProductInfoList());
                    modelMap.put("count", pie.getCount());
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pie.getStateInfo());
                }
            }catch (Exception e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }

        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }

        return modelMap;
    }

    /**
     * 组合查询条件，并将条件封装到productCondition对象里返回
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private ProductInfo compactProductConditionSearch(int shopId, int productCategoryId, String productName) {

        ProductInfo productCondition = new ProductInfo();
        if (shopId != -1) {
            // 查询指定shopId下面的商品列表
            ShopInfo shopInfo = new ShopInfo();
            shopInfo.setId(shopId);
            productCondition.setShopInfo(shopInfo);
        }
        if (productCategoryId != -1) {
            // 查询位于某个商品类别Id下的商品列表
            ProductCategory productCategory = new ProductCategory();
            productCategory.setId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }

        if (productName != null) {
            // 查询名字里包含productName的商品列表
            productCondition.setName(productName);
        }

        // 查询"上架"状态的商品列表
        productCondition.setEnableStatus(1);

        return productCondition;
    }
}
