package com.ponmma.cl.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "admin")
public class AdminController {

    /**
     * 首页路由
     *
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    private String index() {
        return "admin/index";
    }

    /**
     * 个人信息路由
     *
     * @return
     */
    @RequestMapping(value = "personinfo", method = RequestMethod.GET)
    private String personInfo() {
        return "admin/personinfo";
    }

    /**
     * 区域信息路由
     * @return
     */
    @RequestMapping(value = "area", method = RequestMethod.GET)
    private String area() { return "admin/area"; }

    /**
     * 商铺类别路由
     * @return
     */
    @RequestMapping(value = "shopcategory", method = RequestMethod.GET)
    private String shopCategory() { return "admin/shopcategory"; }

    /**
     * 添加商铺类别路由
     * @return
     */
    @RequestMapping(value = "addshopcategory", method = RequestMethod.GET)
    private String addShopCategory() { return "admin/addshopcategory"; }

    /**
     * 修改商铺类别路由
     * @return
     */
    @RequestMapping(value = "editshopcategory", method = RequestMethod.GET)
    private String editShopCategory() { return "admin/editshopcategory"; }
}
