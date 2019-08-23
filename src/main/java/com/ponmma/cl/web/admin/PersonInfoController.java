package com.ponmma.cl.web.admin;

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
@RequestMapping(value = "admin")
public class PersonInfoController {
    @Autowired
    private PersonInfoService personInfoService;

    /**
     * 获取用户初始基本信息
     * @param request
     * @return
     */
    @RequestMapping(value = "getpersoninfo", method = RequestMethod.GET)
    @ResponseBody
    Map<String, Object> getPersonIno(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        if (personInfo == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户不存在");
        }
        else {
            modelMap.put("success", true);
            modelMap.put("personInfo", personInfo);
        }
        return modelMap;
    }

    @RequestMapping(value = "updatepersoninfo", method = RequestMethod.POST)
    @ResponseBody
    Map<String, Object> updatePersonInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("personInfo");
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 接收前端参数的变量的初始化
        String name = HttpServletRequestUtil.getString(request, "name");
        String phone = HttpServletRequestUtil.getString(request, "phone");
        String password = HttpServletRequestUtil.getString(request, "password");
        ImageHolder thumbnail = null;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 处理图片
        try {
            // 若请求中存在文件流，则取出相关的文件
            if (multipartResolver.isMultipart(request))
                thumbnail = handleImage(request, thumbnail);
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 判断手机号是否存在
        if (!personInfo.getPhone().equals(phone)) {
            try {
                PersonInfoExecution pe = personInfoService.getPersonInfoByPhoneAndPasswordAndRole(phone,
                        null, personInfo.getRole());
                if (pe.getState() == PersonInfoEnum.QUERY_SUCCESS.getState()) {
                    if (pe.getPersonInfo() != null) {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", "该手机号已存在");
                        return modelMap;
                    }
                }
                else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                    return modelMap;
                }
            }catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }

        // 更新个人信息
        if (name != null && phone != null && password != null) {
            personInfo.setName(name);
            personInfo.setPhone(phone);
            personInfo.setPassword(password);
            try {
                PersonInfoExecution pe = personInfoService.modifyPersonInfo(personInfo, thumbnail);
                if (pe.getState() == PersonInfoEnum.UPDATE_SUCCESS.getState()) {
                    modelMap.put("success", true);
                    request.getSession().setAttribute("personInfo", pe.getPersonInfo());
                }
                else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        }
        else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入相关个人信息");
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
