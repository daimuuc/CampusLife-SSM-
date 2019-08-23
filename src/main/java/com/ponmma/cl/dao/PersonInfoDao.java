package com.ponmma.cl.dao;

import com.ponmma.cl.entity.PersonInfo;
import org.apache.ibatis.annotations.Param;

public interface PersonInfoDao {

    /**
     * 插入个人基础信息
     * @param personInfo
     * @return
     */
    int insertPersonInfo(PersonInfo personInfo);

    /**
     * 删除个人基础信息
     * @param id
     * @return
     */
    int deletePersonInfo(Integer id);

    /**
     * 更新个人基础信息
     * @param personInfo
     * @return
     */
    int updatePersonInfo(PersonInfo personInfo);

    /**
     * 根据ID查询个人基础信息
     * @param id
     * @return
     */
    PersonInfo queryPersonInfoById(Integer id);

    /**
     * 根据手机号、密码和用户类型查询个人基础信息
     * @param phone
     * @param password
     * @return
     */
    PersonInfo queryPersonInfoByPhoneAndPasswordAndRole(@Param("phone") String phone,
                                                        @Param("password") String password,
                                                        @Param("role") Integer role);

}
