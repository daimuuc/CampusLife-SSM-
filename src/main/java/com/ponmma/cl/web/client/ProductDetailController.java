package com.ponmma.cl.web.client;

import com.ponmma.cl.dao.MutipleImageInfoDao;
import com.ponmma.cl.dto.ProductInfoExecution;
import com.ponmma.cl.entity.MutipleImageInfo;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.enums.ProductInfoEnum;
import com.ponmma.cl.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public class ProductDetailController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private MutipleImageInfoDao mutipleImageInfoDao;

    @RequestMapping(value = "productdetailinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductDetailInfo(HttpServletRequest request, @RequestParam("productId")int productId) {
        Map<String, Object> modelMap = new HashMap<>();

        // 根据Id获取商品信息
        try {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);
            ProductInfoExecution pie = productInfoService.getProductInfo(productInfo);
            if (pie.getState() == ProductInfoEnum.QUERY_SUCCESS.getState()) {
                List<ProductInfo> productInfoList = pie.getProductInfoList();
                if (productInfoList.size() != 0) {
                    productInfo = productInfoList.get(0);
                    // 获取商品详情图
                    List<MutipleImageInfo> mutipleImageInfoList = mutipleImageInfoDao.queryMutipleImageInfoList(productId);
                    productInfo.setMutipleImageInfoList(mutipleImageInfoList);
                    modelMap.put("product", productInfo);
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "商品ID不存在");
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pie.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }
}
