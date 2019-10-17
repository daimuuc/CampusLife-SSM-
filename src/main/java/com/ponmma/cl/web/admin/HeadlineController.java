package com.ponmma.cl.web.admin;

import com.ponmma.cl.dto.HeadLineExecution;
import com.ponmma.cl.enums.HeadLineEnum;
import com.ponmma.cl.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "admin")
public class HeadlineController {
    @Autowired
    private HeadLineService headLineService;

    @RequestMapping(value = "getheadlinelist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHeadlineList() {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 获取头条信息列表
        try {
            // 获取申请中的头条信息
            HeadLineExecution hle = headLineService.getHeadLineList(0);
            if (hle.getState() == HeadLineEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("headlineListZero", hle.getHeadLineList());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", hle.getStateInfo());
                return modelMap;
            }

            // 获取已通过的头条信息
            hle = headLineService.getHeadLineList(1);
            if (hle.getState() == HeadLineEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("headlineListOne", hle.getHeadLineList());
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", hle.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "updateheadlinestatus/{id}/{status}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> updateHeadlineStatus(@PathVariable("id")int id, @PathVariable("status")int status) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 更新头条信息状态
        try {
            HeadLineExecution hle = headLineService.modifyHeadLine(id, status);
            if (hle.getState() == HeadLineEnum.UPDATE_SUCCESS.getState())
                modelMap.put("success", true);
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", hle.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

    @RequestMapping(value = "deleteheadline/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> deleteHeadline(@PathVariable("id")int id) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        // 删除头条信息
        try {
            HeadLineExecution hle = headLineService.removeHeadLine(id);
            if (hle.getState() == HeadLineEnum.REMOVE_SUCCESS.getState())
                modelMap.put("success", true);
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", hle.getStateInfo());
            }
        }catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }

        return modelMap;
    }

}
