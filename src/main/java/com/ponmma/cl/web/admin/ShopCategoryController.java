package com.ponmma.cl.web.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.dto.ShopCategoryExecution;
import com.ponmma.cl.entity.ShopCategory;
import com.ponmma.cl.enums.ShopCategoryEnum;
import com.ponmma.cl.service.ShopCategoryService;
import com.ponmma.cl.util.HttpServletRequestUtil;
import com.ponmma.cl.util.ImageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "admin")
public class ShopCategoryController {
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 获取商铺类别
     * @return
     */
    @RequestMapping(value = "getshopcategory", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getShopCategory() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取商铺类别
        try {
            ShopCategoryExecution se = shopCategoryService.getShopCategoryList();
            if (se.getState() == ShopCategoryEnum.QUERY_SUCCESS.getState()) {
                List<ShopCategory> shopCategoryList = se.getShopCategoryList();
                if (shopCategoryList != null) {
                    modelMap.put("success", true);
                    modelMap.put("shopCategoryList", shopCategoryList);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "获取商铺类别失败");
                }
            }
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取商铺类别失败");
        }

        return modelMap;
    }

    @RequestMapping(value = "getshopcategorybyid", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getShopCategoryById(@RequestParam(value = "id") Integer id, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 判断id是否为空
        if (id == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "ID为空");
            return modelMap;
        }

        // 获取商铺类别
        try {
            ShopCategoryExecution se = shopCategoryService.getShopCategoryById(id);
            if (se.getState() == ShopCategoryEnum.QUERY_SUCCESS.getState()) {
                ShopCategory shopCategory = se.getShopCategory();
                if (shopCategory != null) {
                    request.getSession().setAttribute("shopCategory", shopCategory);
                    modelMap.put("success", true);
                    modelMap.put("name", shopCategory.getName());
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "获取商铺类别失败");
                }
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取商铺类别失败");
        }

        return modelMap;
    }

    @RequestMapping(value = "addshopcategory", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> addShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 接收前端参数的变量的初始化
        ShopCategory shopCategory = new ShopCategory();
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 处理图片
        try {
            // 若请求中存在文件流，则取出相关的文件
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 处理商铺类别信息
        String name = HttpServletRequestUtil.getString(request, "name");
        shopCategory.setName(name);

        // 添加商铺类别
        try {
            ShopCategoryExecution se = shopCategoryService.addShopCategory(shopCategory, thumbnail);
            if (se.getState() == ShopCategoryEnum.ADD_SUCCESS.getState()) {
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

    @RequestMapping(value = "modifyshopcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShopCategory(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 接收前端参数的变量的初始化
        ShopCategory shopCategory = (ShopCategory) request.getSession().getAttribute("shopCategory");
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

        if (shopCategory == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取商铺类别失败");
            return modelMap;
        }

        // 获取ShopCategory
        try {
            String name = HttpServletRequestUtil.getString(request, "name");
            shopCategory.setName(name);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 修改操作
        try {
            ShopCategoryExecution se = shopCategoryService.modifyShopCategory(shopCategory, thumbnail);
            if (se.getState() == ShopCategoryEnum.UPDATE_SUCCESS.getState()) {
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
