package com.ponmma.cl.dao;

import com.ponmma.cl.entity.MutipleImageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MutipleImageInfoDao {

    /**
     * 批量插入多张图片信息
     * @param mutipleImageInfoList
     * @return
     */
    int insertMutipleImageInfoBatch(@Param("mutipleImageInfoList") List<MutipleImageInfo> mutipleImageInfoList);

    /**
     * 根据productInfoId删除多张图片信息
     * @param productInfoId
     * @return
     */
    int deleteMutipleImageInfo(Integer productInfoId);

    /**
     * 根据productInfoId查询多张图片信息
     * @param productInfoId
     * @return
     */
    List<MutipleImageInfo> queryMutipleImageInfoList(Integer productInfoId);
}
