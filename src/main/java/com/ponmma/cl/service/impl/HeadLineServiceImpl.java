package com.ponmma.cl.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ponmma.cl.cache.JedisUtil;
import com.ponmma.cl.dao.HeadLineDao;
import com.ponmma.cl.dto.HeadLineExecution;
import com.ponmma.cl.entity.HeadLine;
import com.ponmma.cl.enums.HeadLineEnum;
import com.ponmma.cl.exceptions.HeadLineException;
import com.ponmma.cl.service.CacheService;
import com.ponmma.cl.service.HeadLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    @Autowired
    private CacheService cacheService;

    @Override
    @Transactional
    public HeadLineExecution addHeadLine(HeadLine headLine) throws HeadLineException {
        headLine.setLastEditTime(new Date());
        headLine.setStatus(0);

        // 插入头条信息
        try {
            int effectNum = headLineDao.insertHeadLine(headLine);
            if (effectNum <= 0) {
                return new HeadLineExecution(HeadLineEnum.ADD_FAILURE);
            }

            // 清空redis相关缓存
            cacheService.removeFromCache(HEADLINELISTKEY);
        }catch (Exception e) {
            e.printStackTrace();
            throw new HeadLineException("插入头条信息失败：" + e.toString());
        }

        return new HeadLineExecution(HeadLineEnum.ADD_SUCCESS, headLine);
    }

    @Override
    @Transactional
    public HeadLineExecution getHeadLineList(int status) throws HeadLineException {
        // 定义redis的key
        String key = HEADLINELISTKEY + status;
        // 定义接收对象
        List<HeadLine> headLineList = null;
        // 定义jackson数据转换操作类
        ObjectMapper mapper = new ObjectMapper();

        // 判断key是否存在
        if (!jedisKeys.exists(key)) {
            // 若不存在，则从数据库里面取出相应数据
            try {
                headLineList = headLineDao.queryHeadLineList(status);
            }catch (Exception e) {
                e.printStackTrace();
                throw new HeadLineException("头条信息查询失败：" + e.toString());
            }
            // 将相关的实体类集合转换成string,存入redis里面对应的key中
            String jsonString;
            try {
                jsonString = mapper.writeValueAsString(headLineList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new HeadLineException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        }else {
            // 若存在，则直接从redis里面取出相应数据
            String jsonString = jedisStrings.get(key);
            // 指定要将string转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                // 将相关key对应的value里的的string转换成对象的实体类集合
                headLineList = mapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                throw new HeadLineException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                throw new HeadLineException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                throw new HeadLineException(e.getMessage());
            }
        }

        return new HeadLineExecution(HeadLineEnum.QUERY_SUCCESS, headLineList);
    }

    @Override
    @Transactional
    public HeadLineExecution removeHeadLine(int id) throws HeadLineException {
        // 删除头条信息
        try {
            int effectNum = headLineDao.deleteHeadLine(id);
            if (effectNum <= 0) {
                return new HeadLineExecution(HeadLineEnum.REMOVE_FAILURE);
            }

            // 清空redis相关缓存
            cacheService.removeFromCache(HEADLINELISTKEY);
        }catch (Exception e) {
            e.printStackTrace();
            throw new HeadLineException("删除头条信息失败：" + e.toString());
        }

        return new HeadLineExecution(HeadLineEnum.REMOVE_SUCCESS, new HeadLine());
    }

    @Override
    @Transactional
    public HeadLineExecution modifyHeadLine(int id, int status) throws HeadLineException {
        // 修改头条信息状态
        try {
            int effectNum = headLineDao.updateHeadLine(id, status);
            if (effectNum <= 0) {
                return new HeadLineExecution(HeadLineEnum.UPDATE_FAILURE);
            }

            // 清空redis相关缓存
            cacheService.removeFromCache(HEADLINELISTKEY);
        }catch (Exception e) {
            e.printStackTrace();
            throw  new HeadLineException("更新头条信息失败：" + e.toString());
        }

        return new HeadLineExecution(HeadLineEnum.UPDATE_SUCCESS, new HeadLine());
    }
}
