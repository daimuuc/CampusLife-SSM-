package com.ponmma.cl.service;

import com.ponmma.cl.dto.AreaExecution;
import com.ponmma.cl.entity.Area;
import com.ponmma.cl.exceptions.AreaException;

import java.util.List;

public interface AreaService {

    /**
     * 插入区域信息
     * @param area
     * @return
     * @throws AreaException
     */
    AreaExecution addArea(Area area) throws AreaException;

    /**
     * 批量插入区域信息
     * @param areaList
     * @return
     * @throws AreaException
     */
    AreaExecution addAreaList(List<Area> areaList) throws AreaException;

    /**
     * 查询区域信息
     * @return
     * @throws AreaException
     */
    AreaExecution getAreaList() throws AreaException;

}
