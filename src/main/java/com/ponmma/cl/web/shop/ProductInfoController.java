package com.ponmma.cl.web.shop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.dto.ProductCategoryExecution;
import com.ponmma.cl.dto.ProductInfoExecution;
import com.ponmma.cl.entity.ProductCategory;
import com.ponmma.cl.entity.ProductInfo;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.enums.ProductCategoryEnum;
import com.ponmma.cl.enums.ProductInfoEnum;
import com.ponmma.cl.service.ProductCategoryService;
import com.ponmma.cl.service.ProductInfoService;
import com.ponmma.cl.util.CodeUtil;
import com.ponmma.cl.util.HttpServletRequestUtil;
import com.ponmma.cl.util.ImageHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    // 商铺信息
    private ShopInfo shopInfo = null;

    // 支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;

    @RequestMapping(value = "getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductCategoryList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取商铺信息
        shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");

        // 获取商品类别
        try {
            ProductCategoryExecution pce = productCategoryService.getProductCategoryByShopInfoId(shopInfo.getId());
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

    @RequestMapping(value = "getproductinfobyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductInfoById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取id
        Integer id = HttpServletRequestUtil.getInt(request, "id");
        if (id == -1) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "ID为空");
            return modelMap;
        }

        // 获取商铺信息
        shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");

        // 获取商品类别
        try {
            ProductCategoryExecution pce = productCategoryService.getProductCategoryByShopInfoId(shopInfo.getId());
            if (pce.getState() == ProductCategoryEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("productCategoryList", pce.getProductCategoryList());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pce.getStateInfo());
                return modelMap;
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 获取商品信息
        try {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(id);
            ProductInfoExecution pie = productInfoService.getProductInfo(productInfo);
            if (pie.getState() == ProductInfoEnum.QUERY_SUCCESS.getState()) {
                productInfo = pie.getProductInfoList().get(0);
                request.getSession().setAttribute("productInfo", productInfo);
                modelMap.put("productInfo", productInfo);
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

    @RequestMapping(value = "addproductinfo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化
        ObjectMapper mapper = new ObjectMapper();
        ProductInfo productInfo = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }

            String productInfoStr = HttpServletRequestUtil.getString(request, "productInfoStr");
            // 尝试获取前端传过来的表单string流并将其转换成ProductInfo实体类
            productInfo = mapper.readValue(productInfoStr, ProductInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (productInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取前端商品信息失败");
            return modelMap;
        }

        // 获取商铺信息
        shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
        if (shopInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询商铺信息失败");
            return modelMap;
        }
        productInfo.setShopInfo(shopInfo);

        // 添加商铺信息
        try {
            ProductInfoExecution pie = productInfoService.addProductInfo(productInfo, thumbnail, productImgList);
            if (pie.getState() == ProductInfoEnum.ADD_SUCCESS.getState()) {
                modelMap.put("success", true);
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

    @RequestMapping(value = "getproductinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取商品名字
        String name = HttpServletRequestUtil.getString(request, "name");
        // 获取商品类别
        Integer id = HttpServletRequestUtil.getInt(request, "productcategoryid");
        if (id == -1)
            id = null;
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        // 获取商铺信息
        shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
        if (shopInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询商铺信息失败");
            return modelMap;
        }

        ProductInfo productInfo = new ProductInfo();
        productInfo.setName(name);
        productInfo.setProductCategory(productCategory);
        productInfo.setShopInfo(shopInfo);

        // 查询商品信息
        try {
            ProductInfoExecution pie = productInfoService.getProductInfo(productInfo);
            if (pie.getState() == ProductInfoEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("producList", pie.getProductInfoList());
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

    @RequestMapping(value = "modifyproductinfo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProductInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 是商品编辑时候调用还是上下架操作的时候调用
        // 若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        // 验证码判断
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化
        ObjectMapper mapper = new ObjectMapper();
        ProductInfo productInfo = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);
            }
            String productInfoStr = HttpServletRequestUtil.getString(request, "productInfoStr");
            // 尝试获取前端传过来的表单string流并将其转换成ProductInfo实体类
            productInfo = mapper.readValue(productInfoStr, ProductInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        if (productInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "获取前端商品信息失败");
            return modelMap;
        }

        // 获取商铺信息
        shopInfo = (ShopInfo) request.getSession().getAttribute("shopInfo");
        if (shopInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询商铺信息失败");
            return modelMap;
        }
        productInfo.setShopInfo(shopInfo);

        // 修改商铺信息
        try {
            if (!statusChange)
                // 获取原始productInfo的缩略图
                productInfo.setSingleImageInfo(((ProductInfo)request.getSession().getAttribute("productInfo")).getSingleImageInfo());
            ProductInfoExecution pie = productInfoService.modifyProductInfo(productInfo, thumbnail, productImgList);
            if (pie.getState() == ProductInfoEnum.UPDATE_SUCCESS.getState()) {
                modelMap.put("success", true);
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

    private ImageHolder handleImage(HttpServletRequest request, ImageHolder thumbnail, List<ImageHolder> productImgList)
            throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        if (thumbnailFile != null) {
            thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        }
        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGEMAXCOUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
                        productImgFile.getInputStream());
                productImgList.add(productImg);
            } else {
                // 若取出的第i个详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }

}
