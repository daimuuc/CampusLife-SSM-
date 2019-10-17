package com.ponmma.cl.dao;

import com.ponmma.cl.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HeadLineDao {

    /**
     * 插入头条信息
     * @param headLine
     * @return
     */
    int insertHeadLine(HeadLine headLine);

    /**
     * 根据状态查询所有头条信息
     * @return
     */
    List<HeadLine> queryHeadLineList(int status);

    /**
     * 根据id删除指定头条信息
     * @param id
     * @return
     */
    int deleteHeadLine(int id);

    /**
     * 更新指定头条信息的状态
     * @param id
     * @param status
     * @return
     */
    int updateHeadLine(@Param("id") int id, @Param("status") int status);

}
