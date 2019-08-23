package com.ponmma.cl.service;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.dao.PersonInfoDao;
import com.ponmma.cl.dto.PersonInfoExecution;
import com.ponmma.cl.entity.PersonInfo;
import com.ponmma.cl.util.ImageHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;

public class PersonInfoServiceTest extends BaseTest {
    @Autowired
    private PersonInfoService personInfoService;
    @Autowired
    private PersonInfoDao personInfoDao;

    @Test
    public void testAddPersonInfo() throws Exception{
        File file = new File("/Users/antony/Desktop/profile.jpg");
        ImageHolder imageHolder = new ImageHolder(file.getName(), new FileInputStream(file));
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("test");
        personInfo.setRole(0);
        personInfo.setPassword("1234");
        personInfo.setPhone("12345678901");
        PersonInfoExecution personInfoExecution = personInfoService.addPersonInfo(personInfo, imageHolder);
        if (personInfoExecution.getState() == 1)
            System.out.println(personInfoExecution.getPersonInfo());
        else
            System.out.println(personInfoExecution.getStateInfo());
    }

    @Test
    public void testModifyPersonInfo() throws Exception{
        PersonInfo personInfo = personInfoDao.queryPersonInfoById(3);
        personInfo.setName("test3");
        personInfo.setPassword("43210");
        File file = new File("/Users/antony/Desktop/license.jpg");
        ImageHolder imageHolder = new ImageHolder(file.getName(), new FileInputStream(file));
        PersonInfoExecution personInfoExecution = personInfoService.modifyPersonInfo(personInfo, imageHolder);
        if (personInfoExecution.getState() == 2)
            System.out.println(personInfoExecution.getPersonInfo());
        else
            System.out.println(personInfoExecution.getStateInfo());
    }

}
