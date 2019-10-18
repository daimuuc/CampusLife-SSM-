package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.Area;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Ignore
    @Test
    public void testInsertArea() {
        Area area = new Area();
        area.setName("西苑");
        area.setLastEditTime(new Date());
        int effectNum = areaDao.insertArea(area);
        assertEquals(1, effectNum);
        System.out.println(area.getId());
    }

    @Ignore
    @Test
    public void testQueryAreaList() {
        List<Area> list = areaDao.queryAreaList();
        assertEquals(1, list.size());
    }

    @Ignore
    @Test
    public void testUpdateArea() {
        Area area = areaDao.queryAreaList().get(0);
        area.setName("南苑");
        area.setLastEditTime(new Date());
        int effectNum = areaDao.updateArea(area);
        assertEquals(1, effectNum);
    }

    @Ignore
    @Test
    public void testDeleteArea() {
        Integer id = 1;
        int effectNum = areaDao.deleteArea(id);
        assertEquals(1, effectNum);
    }
}
