package com.ponmma.cl.web.admin;

import com.ponmma.cl.dto.AreaExecution;
import com.ponmma.cl.entity.Area;
import com.ponmma.cl.enums.AreaEnum;
import com.ponmma.cl.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "admin")
public class AreaController {
    @Autowired
    AreaService areaService;

    /**
     * 获取区域信息
     * @return
     */
    @RequestMapping(value = "getarea", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getArea() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        try {
            AreaExecution areaExecution = areaService.getAreaList();
            if (areaExecution.getState() == AreaEnum.QUERY_SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("data", areaExecution.getAreaList());
            }
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "查询失败");
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "查询失败");
        }

        return modelMap;
    }

    @RequestMapping(value = "addarea", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addArea(@RequestBody List<Area> areaList) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int size = areaList.size();

        // 判断是否为空
        if (size == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请完整填写相关信息");
            return modelMap;
        }

        // 添加区域信息
        try {
            AreaExecution areaExecution = areaService.addAreaList(areaList);
            if (areaExecution.getState() == AreaEnum.ADD_SUCCESS.getState()) {
                modelMap.put("success", true);
            }
            else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "添加区域信息出错");
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "添加区域信息出错");
        }

        return modelMap;
    }
}
