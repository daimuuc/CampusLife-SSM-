package com.ponmma.cl.dao;

import com.ponmma.cl.entity.SingleImageInfo;

public interface SingleImageInfoDao {

    /**
     * 插入单一图片信息
     * @param singleImageInfo
     * @return
     */
    int insertSingleImageInfo(SingleImageInfo singleImageInfo);

    /**
     * 删除单一图片信息
     * @param id
     * @return
     */
    int deleteSingleImageInfo(Integer id);

    /**
     * 更新单一图片信息
     * @param singleImageInfo
     * @return
     */
    int updateSingleImageInfo(SingleImageInfo singleImageInfo);

    /**
     * 查询单一图片信息
     * @param id
     * @return
     */
    SingleImageInfo querySingleImageInfo(Integer id);

}
