package com.ponmma.cl.service;

import com.ponmma.cl.dto.HeadLineExecution;
import com.ponmma.cl.entity.HeadLine;
import com.ponmma.cl.exceptions.HeadLineException;

public interface HeadLineService {
    String HEADLINELISTKEY = "cl_headlinelist_";

    /**
     * 插入头条信息
     * @param headLine
     * @return
     * @throws HeadLineException
     */
    HeadLineExecution addHeadLine(HeadLine headLine) throws HeadLineException;

    /**
     * 根据状态查询所有头条信息
     * @return
     * @throws HeadLineException
     */
    HeadLineExecution getHeadLineList(int status) throws HeadLineException;

    /**
     * 根据id删除指定头条信息
     * @param id
     * @return
     * @throws HeadLineException
     */
    HeadLineExecution removeHeadLine(int id) throws HeadLineException;

    /**
     * 更新指定头条信息的状态
     * @param id
     * @param status
     * @return
     * @throws HeadLineException
     */
    HeadLineExecution modifyHeadLine(int id, int status) throws HeadLineException;

}
