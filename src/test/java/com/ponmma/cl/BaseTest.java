package com.ponmma.cl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 配置spring和junit整合，junit启动时加载springIOC容器
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件的位置
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml",
"classpath:spring/spring-redis.xml"})
public class BaseTest {

    @Test
    public void test() {

    }

}
