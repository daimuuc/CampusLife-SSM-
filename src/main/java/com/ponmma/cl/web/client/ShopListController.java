package com.ponmma.cl.web.client;

import com.ponmma.cl.dto.AreaExecution;
import com.ponmma.cl.dto.ShopCategoryExecution;
import com.ponmma.cl.dto.ShopInfoExecution;
import com.ponmma.cl.entity.Area;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.AreaEnum;
import com.ponmma.cl.enums.ShopCategoryEnum;
import com.ponmma.cl.enums.ShopInfoEnum;
import com.ponmma.cl.service.AreaService;
import com.ponmma.cl.service.ShopCategoryService;
import com.ponmma.cl.service.ShopInfoService;
import com.ponmma.cl.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "client")
public class ShopListController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopInfoService shopInfoService;

    @RequestMapping(value = "listindexinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listIndexInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取商铺类别
        try {
            ShopCategoryExecution se = shopCategoryService.getShopCategoryList();
            if (se.getState() == ShopCategoryEnum.QUERY_SUCCESS.getState()) {
                if (se.getShopCategoryList() != null) {
                    modelMap.put("success", true);
                    modelMap.put("shopCategoryList", se.getShopCategoryList());
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "商铺类别查询失败");
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

        // TODO 获取HeadLine

        return modelMap;
    }

    @RequestMapping(value = "listshoplistinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopListInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取商店类别ID
        int id = HttpServletRequestUtil.getInt(request, "id");
        if (id == -1) {
            // 获取商铺类别
            try {
                ShopCategoryExecution se = shopCategoryService.getShopCategoryList();
                if (se.getState() == ShopCategoryEnum.QUERY_SUCCESS.getState()) {
                    if (se.getShopCategoryList() != null) {
                        modelMap.put("shopCategoryList", se.getShopCategoryList());
                    }else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", "商铺类别查询失败");
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
        }

        // 获取区域信息
        try {
            AreaExecution ae = areaService.getAreaList();
            if (ae.getState() == AreaEnum.QUERY_SUCCESS.getState()) {
                if (ae.getAreaList() != null) {
                    modelMap.put("success", true);
                    modelMap.put("areaList", ae.getAreaList());
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "区域信息查询失败");
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", ae.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "shoplistinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> shopListInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取一页需要显示的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 非空判断
        if ((pageIndex > -1) && (pageSize > -1)) {
            // 试着获取指定商店类别Id
            int shopCategoryId = HttpServletRequestUtil.getInt(request, "shopCategoryId");
            // 试着获取区域Id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            // 试着获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            // 获取组合之后的查询条件
            ShopInfo shopCondition = compactShopConditionSearch(shopCategoryId, areaId, shopName);

            try {
                // 根据查询条件和分页信息获取店铺列表，并返回总数
                ShopInfoExecution se = shopInfoService.getShopInfoListCondition(shopCondition, pageIndex, pageSize);
                if (se.getState() == ShopInfoEnum.QUERY_SUCCESS.getState()) {
                    modelMap.put("shopList", se.getShopInfoList());
                    modelMap.put("count", se.getCount());
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());
                }
            }catch (Exception e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }


        return modelMap;
    }

    /**
     * 组合查询条件，并将条件封装到ShopCondition对象里返回
     *
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private ShopInfo compactShopConditionSearch(int shopCategoryId, int areaId, String shopName) {

        ShopInfo shopCondition = new ShopInfo();
        if (shopCategoryId != -1) {
            // 查询指定ShopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1) {
            // 查询位于某个区域Id下的店铺列表
            Area area = new Area();
            area.setId(areaId);
            shopCondition.setArea(area);
        }

        if (shopName != null) {
            // 查询名字里包含shopName的店铺列表
            PersonInfo personInfo = new PersonInfo();
            personInfo.setName(shopName);
            shopCondition.setPersonInfo(personInfo);
        }

        return shopCondition;
    }

}
