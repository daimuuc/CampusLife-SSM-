package com.ponmma.cl.web.shop;

import com.ponmma.cl.dto.ProductCategoryExecution;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.ProductCategoryEnum;
import com.ponmma.cl.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "shop")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取商品类别
     * @return
     */
    @RequestMapping(value = "getproductcategory", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getProductCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            ShopInfo shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
            ProductCategoryExecution pce = productCategoryService.getProductCategoryByShopInfoId(shopInfo.getId());
            if (pce.getState() == ProductCategoryEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("data", pce.getProductCategoryList());
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

    @RequestMapping(value = "addproductcategory", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addProductCategory(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int size = productCategoryList.size();

        // 判断是否为空
        if (size == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请完整填写相关信息");
            return modelMap;
        }

        // 获取ShopInfo
        ShopInfo shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
        for (ProductCategory productCategory : productCategoryList)
            productCategory.setShopInfoId(shopInfo.getId());

        // 添加商品类别信息
        try {
             ProductCategoryExecution pce = productCategoryService.addProductCategoryBatch(productCategoryList);
            if (pce.getState() == ProductCategoryEnum.ADD_SUCCESS.getState()) {
                modelMap.put("success", true);
            }
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "添加商品类别信息出错");
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "添加商品类别信息出错");
        }

        return modelMap;
    }
}
