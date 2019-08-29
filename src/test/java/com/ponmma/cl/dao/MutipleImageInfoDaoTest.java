package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.MutipleImageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MutipleImageInfoDaoTest extends BaseTest {
    @Autowired
    private MutipleImageInfoDao mutipleImageInfoDao;

    @Test
    public void testInsertMutipleImageInfoBatch() {
        List<MutipleImageInfo> mutipleImageInfoList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MutipleImageInfo mutipleImageInfo = new MutipleImageInfo();
            mutipleImageInfo.setSrc("1");
            Integer productInfoId = 2;
            mutipleImageInfo.setProductInfoId(productInfoId);
            mutipleImageInfoList.add(mutipleImageInfo);
        }

        int effectNum = mutipleImageInfoDao.insertMutipleImageInfoBatch(mutipleImageInfoList);
        assertEquals(3, effectNum);
        System.out.println(mutipleImageInfoList);
    }

    @Test
    public void testQueryMutipleImageInfoList() {
        Integer productInfoId = 2;
        List<MutipleImageInfo> mutipleImageInfoList = mutipleImageInfoDao.queryMutipleImageInfoList(productInfoId);
        assertEquals(3, mutipleImageInfoList.size());
        System.out.println(mutipleImageInfoList);
    }

    @Test
    public void testDeleteMutipleImageInfo() {
        Integer productInfoId = 2;
        int effectNum = mutipleImageInfoDao.deleteMutipleImageInfo(productInfoId);
        assertEquals(3, effectNum);
    }
}
