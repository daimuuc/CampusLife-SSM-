package com.ponmma.cl.dao;

import com.ponmma.cl.entity.Area;

import java.util.List;

public interface AreaDao {

    /**
     * 插入区域信息
     * @param area
     * @return
     */
    int insertArea(Area area);

    /**
     * 删除区域信息
     * @param id
     * @return
     */
    int deleteArea(Integer id);

    /**
     * 更新区域信息
     * @param area
     * @return
     */
    int updateArea(Area area);

    /**
     * 查询所有区域信息
     * @return
     */
    List<Area> queryAreaList();
}
