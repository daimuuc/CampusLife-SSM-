package com.ponmma.cl.service;

import com.ponmma.cl.dto.PersonInfoExecution;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.exceptions.PersonInfoException;
import com.ponmma.cl.util.ImageHolder;

public interface PersonInfoService {

    /**
     * 插入个人基本信息
     * @param personInfo
     * @param thumbnail
     * @return
     * @throws PersonInfoException
     */
    PersonInfoExecution addPersonInfo(PersonInfo personInfo, ImageHolder thumbnail) throws PersonInfoException;

    /**
     * 更新个人基础信息
     * @param personInfo
     * @return
     */
    PersonInfoExecution modifyPersonInfo(PersonInfo personInfo, ImageHolder thumbnail) throws PersonInfoException;

    /**
     * 查询个人基础信息
     * @param
     * @return
     */
    PersonInfoExecution getPersonInfoByPhoneAndPasswordAndRole(String phone, String password, Integer role) throws PersonInfoException;
}
