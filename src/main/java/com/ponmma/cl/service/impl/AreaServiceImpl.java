package com.ponmma.cl.service.impl;

import com.ponmma.cl.dao.AreaDao;
import com.ponmma.cl.dto.AreaExecution;
import com.ponmma.cl.entity.Area;
import com.ponmma.cl.enums.AreaEnum;
import com.ponmma.cl.exceptions.AreaException;
import com.ponmma.cl.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

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
        List<Area> areaList = null;
        try {
            areaList = areaDao.queryAreaList();
            if (areaList == null) {
                throw new AreaException("区域信息查询失败");
            }
        }catch (Exception e) {
            throw new AreaException("区域信息查询失败");
        }

        return new AreaExecution(AreaEnum.QUERY_SUCCESS, areaList);
    }
}
