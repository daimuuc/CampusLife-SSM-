package com.ponmma.cl.web.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.dto.PersonInfoExecution;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.enums.PersonInfoEnum;
import com.ponmma.cl.service.PersonInfoService;
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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "client")
public class PersonController {
    @Autowired
    private PersonInfoService personInfoService;

    @RequestMapping(value = "getpersoninfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getPersonInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取个人信息
        try {
            PersonInfo personInfo = (PersonInfo)request.getSession().getAttribute("personInfo");
            modelMap.put("success", true);
            modelMap.put("personInfo", personInfo);
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "updatepersoninfo", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> updatePersonInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化
        ObjectMapper mapper = new ObjectMapper();
        PersonInfo personInfo = null;
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 处理个人基本信息
        try {
            String personInfoStr = HttpServletRequestUtil.getString(request, "personInfoStr");
            // 尝试获取前端传过来的表单string流并将其转换成PersonInfo实体类
            personInfo = mapper.readValue(personInfoStr, PersonInfo.class);
            // 设置Id
            personInfo.setId(((PersonInfo)request.getSession().getAttribute("personInfo")).getId());
            // 设置原始图片信息
            personInfo.setSingleImageInfo(((PersonInfo)request.getSession().getAttribute("personInfo")).getSingleImageInfo());
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 处理图片
        try {
            // 若请求中存在文件流，则取出相关的文件
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail);
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 修改个人信息
        try {
            PersonInfoExecution pie = personInfoService.modifyPersonInfo(personInfo, thumbnail);
            if (pie.getState() == PersonInfoEnum.UPDATE_SUCCESS.getState()) {
                request.getSession().setAttribute("personInfo", pie.getPersonInfo());
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
