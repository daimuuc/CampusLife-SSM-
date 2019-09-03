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

    @RequestMapping(value = "shoplist", method = RequestMethod.GET)
    private String shopList() { return "client/shoplist"; }

}
