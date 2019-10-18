package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.HeadLine;
import com.ponmma.cl.entity.ShopInfo;
import com.ponmma.cl.entity.SingleImageInfo;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HeadLineDaoTest extends BaseTest {
    @Autowired
    private HeadLineDao headLineDao;

    @Ignore
    @Test
    public void testInsertHeadLine() {
        HeadLine headLine = new HeadLine();
        headLine.setLastEditTime(new Date());
        headLine.setStatus(0);
        SingleImageInfo singleImageInfo = new SingleImageInfo();
        singleImageInfo.setId(34);
        headLine.setSingleImageInfo(singleImageInfo);
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(8);
        headLine.setShopInfo(shopInfo);

        int effectNum = headLineDao.insertHeadLine(headLine);
        assertEquals(1, effectNum);
        System.out.println(headLine);
    }

    @Ignore
    @Test
    public void testQueryHeadLineList() {
        List<HeadLine> headLineList = headLineDao.queryHeadLineList(1);
        assertEquals(2, headLineList.size());
        System.out.println(headLineList);
    }

    @Ignore
    @Test
    public void testDeleteHeadLine() {
        int id = 1;

        int effectNum = headLineDao.deleteHeadLine(id);
        assertEquals(1, effectNum);
    }

    @Ignore
    @Test
    public void testUpdateHeadLine() {
        int id = 2, status = 1;

        int effectNum = headLineDao.updateHeadLine(id, status);
        assertEquals(1, effectNum);
    }
}
