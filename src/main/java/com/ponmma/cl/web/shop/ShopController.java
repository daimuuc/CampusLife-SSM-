package com.ponmma.cl.web.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shop")
public class ShopController {

    /**
     * 首页路由
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    private String index() { return "shop/index"; }

    /**
     * 商铺信息路由
     * @return
     */
    @RequestMapping(value = "shopinfo", method = RequestMethod.GET)
    private String shopInfo() { return "shop/shopinfo"; }

    /**
     * 商铺注册路由
     * @return
     */
    @RequestMapping(value = "enroll", method = RequestMethod.GET)
    private String enroll() { return "shop/enroll"; }

    /**
     * 商品类别路由
     * @return
     */
    @RequestMapping(value = "productcategory", method = RequestMethod.GET)
    private String productCategory() { return "shop/productcategory"; }
}
