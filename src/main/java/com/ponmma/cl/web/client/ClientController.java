package com.ponmma.cl.web.client;

import com.ponmma.cl.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "client")
public class ClientController {

    /**
     * 首页路由
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    private String index() { return "client/index"; }

    /**
     * 商店列表路由
     * @return
     */
    @RequestMapping(value = "shoplist", method = RequestMethod.GET)
    private String shopList() { return "client/shoplist"; }

    /**
     * 商店详情路由
     * @return
     */
    @RequestMapping(value = "shopdetail", method = RequestMethod.GET)
    private String shopDetail() { return "client/shopdetail"; }

    @RequestMapping(value = "productdetail", method = RequestMethod.GET)
    private String productDetail() { return "client/productdetail"; }

}
