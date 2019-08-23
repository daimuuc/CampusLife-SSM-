package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class PersonInfoDaoTest extends BaseTest {
    @Autowired
    PersonInfoDao personInfoDao;

    @Test
    public void testInsertPersonInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("test1");
        personInfo.setPassword("123");
        personInfo.setPhone("12345678901");
        personInfo.setRole(1);
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(3);
        personInfo.setSingleImageInfo(singleImageInfo);
        int effectNum = personInfoDao.insertPersonInfo(personInfo);
        assertEquals(1, effectNum);
        System.out.println("ID : " + personInfo.getId());
    }

    @Test
    public void testUpdatePersonInfo() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setId(1);
        personInfo.setName("test2");
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(1);
        personInfo.setSingleImageInfo(singleImageInfo);
        int effectNum = personInfoDao.updatePersonInfo(personInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQueryPersonInfoById() {
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(1);
        System.out.println(personInfo);
    }

    @Test
    public void testQueryPersonInfoByPhoneAndPassword() {
        PersonInfo personInfo = personInfoDao.queryPersonInfoByPhoneAndPasswordAndRole("12345678901", "43210", 0);
        System.out.println(personInfo);
    }

    @Test
    public void testDeleteSingleImageInfo() {
        int effectNum = personInfoDao.deletePersonInfo(1);
        assertEquals(1, effectNum);
    }
}
