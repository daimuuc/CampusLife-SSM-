package com.ponmma.cl.service;

import com.ponmma.cl.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CacheServiceTest extends BaseTest {
    @Autowired
    private CacheService cacheService;

    @Test
    public void testRemoveFromCache() {
        // 清除redis所有缓存数据
        String keyPrefix = "";
        cacheService.removeFromCache(keyPrefix);
    }
}
