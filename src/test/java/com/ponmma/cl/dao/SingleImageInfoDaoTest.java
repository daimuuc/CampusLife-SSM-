package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.SingleImageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class SingleImageInfoDaoTest extends BaseTest {

    @Autowired
    SingleImageInfoDao singleImageInfoDao;

    @Test
    public void testInsertSingleImageInfo() {
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setSrc("test1");
        int effectNum = singleImageInfoDao.insertSingleImageInfo(singleImageInfo);
        assertEquals(1, effectNum);
        System.out.println("ID : " + singleImageInfo.getId());
    }

    @Test
    public void testUpdateSingleImageInfo() {
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(2);
        singleImageInfo.setSrc("test2");
        int effectNum = singleImageInfoDao.updateSingleImageInfo(singleImageInfo);
        assertEquals(1, effectNum);
    }

    @Test
    public void testQuerySingleImageInfo() {
        SingleImageInfo singleImageInfo = singleImageInfoDao.querySingleImageInfo(2);
        System.out.println(singleImageInfo);
    }

    @Test
    public void testDeleteSingleImageInfo() {
        int effectNum = singleImageInfoDao.deleteSingleImageInfo(2);
        assertEquals(1, effectNum);
    }

}
