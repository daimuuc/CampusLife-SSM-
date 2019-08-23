package com.ponmma.cl.dao;

import com.ponmma.cl.BaseTest;
import com.ponmma.cl.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testInsertArea() {
        Area area = new Area();
        area.setName("西苑");
        area.setLastEditTime(new Date());
        int effectNum = areaDao.insertArea(area);
        assertEquals(1, effectNum);
        System.out.println(area.getId());
    }

    @Test
    public void testQueryAreaList() {
        List<Area> list = areaDao.queryAreaList();
        assertEquals(1, list.size());
    }

    @Test
    public void testUpdateArea() {
        Area area = areaDao.queryAreaList().get(0);
        area.setName("南苑");
        area.setLastEditTime(new Date());
        int effectNum = areaDao.updateArea(area);
        assertEquals(1, effectNum);
    }

    @Test
    public void testDeleteArea() {
        Integer id = 1;
        int effectNum = areaDao.deleteArea(id);
        assertEquals(1, effectNum);
    }
}
