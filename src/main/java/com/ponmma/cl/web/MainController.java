package com.ponmma.cl.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.dto.PersonInfoExecution;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.enums.PersonInfoEnum;
import com.ponmma.cl.exceptions.PersonInfoException;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/main")
public class MainController {
    @Autowired
    private PersonInfoService personInfoService;

    /**
     * 登录路由
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    private String login() {
        return "login";
    }

    /**
     * 登录相关逻辑处理
     * @param request
     * @return
     */
    @RequestMapping(value = "/loginlogic", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginLogic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 获取输入的手机号
        String phone = HttpServletRequestUtil.getString(request, "phone");
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 获取输入的用户类型
        Integer role = HttpServletRequestUtil.getInt(request,"role");

        // 登录操作
        if (phone != null && password != null && role != null) {
            try {
                PersonInfoExecution pe = personInfoService.getPersonInfoByPhoneAndPasswordAndRole(phone, password, role);
                if (pe.getState() == PersonInfoEnum.QUERY_SUCCESS.getState()) {
                    if (pe.getPersonInfo() != null) {
                        modelMap.put("success", true);
                        request.getSession().setAttribute("personInfo", pe.getPersonInfo());
                    }
                    else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", "用户不存在");
                    }
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
            modelMap.put("errMsg", "请输入相关登录信息");
        }
        return modelMap;
    }

    /**
     * 注册路由
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.GET)
    private String enroll() {
        return "enroll";
    }

    /**
     * 注册相关逻辑处理
     * @param request
     * @return
     */
    @RequestMapping(value = "enrolllogic", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> enrollLogic(HttpServletRequest request) {
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

        // 处理个人基本信息
        try {
            String personInfoStr = HttpServletRequestUtil.getString(request, "personInfoStr");
            // 尝试获取前端传过来的表单string流并将其转换成PersonInfo实体类
            personInfo = mapper.readValue(personInfoStr, PersonInfo.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }

        // 注册操作
        if (thumbnail != null && personInfo != null) {
            try {
                PersonInfoExecution pe = personInfoService.addPersonInfo(personInfo, thumbnail);
                if (pe.getState() == PersonInfoEnum.ADD_SUCCEESS.getState()) {
                    modelMap.put("success", true);
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            }
            catch (PersonInfoException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        }
        else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入相关注册信息");
        }

        return modelMap;
    }

    /**
     * 找回密码路由
     * @return
     */
    @RequestMapping(value = "find", method = RequestMethod.GET)
    private String find() {
        return "find";
    }

    /**
     * 找回密码相关逻辑处理
     * @param request
     * @return
     */
    @RequestMapping(value = "findlogic", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> findLogic(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }

        // 获取输入的手机号
        String phone = HttpServletRequestUtil.getString(request, "phone");
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 获取输入的用户类型
        Integer role = HttpServletRequestUtil.getInt(request,"role");

        // 更新密码
        if (phone != null && password != null && role != null) {
            try {
                // 查询用户是否存在
                PersonInfoExecution pe = personInfoService.getPersonInfoByPhoneAndPasswordAndRole(phone, null, role);
                if (pe.getState() == PersonInfoEnum.QUERY_SUCCESS.getState()) {
                    if (pe.getPersonInfo() != null) {
                        // 更新操作
                        PersonInfo personInfo = pe.getPersonInfo();
                        personInfo.setPassword(password);
                        try {
                            pe = personInfoService.modifyPersonInfo(personInfo, null);
                            if (pe.getState() == PersonInfoEnum.UPDATE_SUCCESS.getState()) {
                                modelMap.put("success", true);
                            }
                            else {
                                modelMap.put("success", false);
                                modelMap.put("errMsg", pe.getStateInfo());
                            }
                        }
                        catch (Exception e) {
                            modelMap.put("success", false);
                            modelMap.put("errMsg", e.toString());
                        }
                    }
                    else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", "用户不存在");
                    }
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
            modelMap.put("errMsg", "请输入相关信息");
        }

        return modelMap;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 将用户session置为空
        request.getSession().setAttribute("personInfo", null);
        modelMap.put("success", true);
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
