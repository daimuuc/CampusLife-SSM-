package com.ponmma.cl.web.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.dto.AreaExecution;
import com.ponmma.cl.dto.PersonInfoExecution;
import com.ponmma.cl.dto.ShopCategoryExecution;
import com.ponmma.cl.dto.ShopInfoExecution;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.AreaEnum;
import com.ponmma.cl.enums.PersonInfoEnum;
import com.ponmma.cl.enums.ShopCategoryEnum;
import com.ponmma.cl.enums.ShopInfoEnum;
import com.ponmma.cl.service.AreaService;
import com.ponmma.cl.service.PersonInfoService;
import com.ponmma.cl.service.ShopCategoryService;
import com.ponmma.cl.service.ShopInfoService;
import com.ponmma.cl.util.CodeUtil;
import com.ponmma.cl.util.HttpServletRequestUtil;
import com.ponmma.cl.util.ImageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "shop")
public class ShopInfoController {
    @Autowired
    private ShopInfoService shopInfoService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> info(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

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

        // 获取区域类别
        try {
            AreaExecution ae = areaService.getAreaList();
            if (ae.getState() == AreaEnum.QUERY_SUCCESS.getState()) {
                if (ae.getAreaList() != null) {
                    modelMap.put("success", true);
                    modelMap.put("areaList", ae.getAreaList());
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "区域类别查询失败");
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

        // 获取商铺信息
        modelMap.put("shopInfo", request.getSession().getAttribute("shopInfo"));
        return modelMap;
    }

    @RequestMapping(value = "enroll", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> enroll(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化
        ObjectMapper mapper = new ObjectMapper();
        ShopInfo shopInfo = null;
        try {
            String shopInfoStr = HttpServletRequestUtil.getString(request, "shopInfoStr");
            // 尝试获取前端传过来的表单string流并将其转换成ShopInfo实体类
            shopInfo = mapper.readValue(shopInfoStr, ShopInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (shopInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取前端商铺信息失败");
            return modelMap;
        }

        // 获取用户信息
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (personInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询用户信息失败");
            return modelMap;
        }
        shopInfo.setPersonInfo(personInfo);

        // 注册操作
        try {
            ShopInfoExecution se = shopInfoService.addShopInfo(shopInfo);
            if (se.getState() == ShopInfoEnum.ADD_SUCCESS.getState()) {
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

        return modelMap;
    }

    @RequestMapping(value = "updateshopinfo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> updateShopInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化，包括商铺信息，缩略图实体类
        ObjectMapper mapper = new ObjectMapper();
        ShopInfo shopInfo = null;
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 若请求中存在文件流，则取出相关的文件
        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 获取商铺信息
        try {
            String shopInfoStr = HttpServletRequestUtil.getString(request, "shopInfoStr");
            // 尝试获取前端传过来的表单string流并将其转换成ShopInfo实体类
            shopInfo = mapper.readValue(shopInfoStr, ShopInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 更新操作
        if (shopInfo != null) {
            ShopInfo tempShopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
            shopInfo.setId(tempShopInfo.getId());
            PersonInfo personInfo = shopInfo.getPersonInfo();
            personInfo.setId(tempShopInfo.getPersonInfo().getId());
            personInfo.setSingleImageInfo(tempShopInfo.getPersonInfo().getSingleImageInfo());
            shopInfo.setPersonInfo(personInfo);

            // 更新用户信息
            try {
                PersonInfoExecution pe = personInfoService.modifyPersonInfo(personInfo, thumbnail);
                if (pe.getState() != PersonInfoEnum.UPDATE_SUCCESS.getState()) {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                    return modelMap;
                }
            }catch (Exception e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

            // 更新商铺信息
            try {
                ShopInfoExecution se = shopInfoService.modifyShopInfo(shopInfo);
                if (se.getState() == ShopInfoEnum.UPDATE_SUCCESS.getState()) {
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
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取前端商铺信息失败");
        }

        return modelMap;
    }

    @RequestMapping(value = "getshopinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取用户
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (personInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户不存在");
            return modelMap;
        }

        // 查询商铺信息
        try {
            ShopInfoExecution se = shopInfoService.getShopInfoByPersonInfoId(personInfo.getId());
            if (se.getState() == ShopInfoEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("shopInfo", se.getShopInfo());
                request.getSession().setAttribute("shopInfo", se.getShopInfo());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        return thumbnail;
    }

}
