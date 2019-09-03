package com.ponmma.cl.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.cache.JedisUtil;
import com.ponmma.cl.dao.AreaDao;
import com.ponmma.cl.dto.AreaExecution;
import com.ponmma.cl.entity.Area;
import com.ponmma.cl.enums.AreaEnum;
import com.ponmma.cl.exceptions.AreaException;
import com.ponmma.cl.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;

    @Override
    @Transactional
    public AreaExecution addArea(Area area) {
        area.setLastEditTime(new Date());
        try {
            int effectNum = areaDao.insertArea(area);
            if (effectNum <= 0) {
                throw new AreaException("区域信息添加失败");
            }
        }catch (Exception e) {
            throw new AreaException("区域信息添加失败");
        }

        return new AreaExecution(AreaEnum.ADD_SUCCESS, area);
    }

    @Override
    @Transactional
    public AreaExecution addAreaList(List<Area> areaList) throws AreaException {
        try {
            for (Area area : areaList) {
                area.setLastEditTime(new Date());
                int effectNum = areaDao.insertArea(area);
                if (effectNum <= 0)
                    throw new AreaException("区域信息添加失败");
            }
        }
        catch (Exception e) {
            throw new AreaException("区域信息添加失败");
        }

        return new AreaExecution(AreaEnum.ADD_SUCCESS, areaList);
    }

    @Override
    @Transactional
    public AreaExecution getAreaList() {
        // 定义redis的key
        String key = AREALISTKEY;
        // 定义接收对象
        List<Area> areaList = null;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();

        // 判断key是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库里面取出相应数据
            try {
                areaList = areaDao.queryAreaList();
                if (areaList == null) {
                    throw new AreaException("区域信息查询失败");
                }
            }catch (Exception e) {
                throw new AreaException("区域信息查询失败");
            }
            // 将相关的实体类集合转换成string,存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(areaList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new AreaException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        }else {
            // 若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            // 指定要将string转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                // 将相关key对应的value里的的string转换成对象的实体类集合
                areaList = mapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                throw new AreaException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                throw new AreaException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                throw new AreaException(e.getMessage());
            }
        }

        return new AreaExecution(AreaEnum.QUERY_SUCCESS, areaList);
    }
}
