package org.redis.distributed.core;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.redis.distributed.core.intercept.CommonAspect;
import org.redis.distributed.core.limit.RedisLimit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommonAspectTest {

    @InjectMocks
    private CommonAspect commonAspect;

    @Mock
    private RedisLimit redisLimit;

    @Before
    public void setBefore() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() {
        // 当调用redisLimit.limit()返回true
        Mockito.when(redisLimit.limit()).thenReturn(true);
        commonAspect.before(null);
        boolean limit = redisLimit.limit();
        System.out.println(limit);
        assertTrue(limit);
        Mockito.verify(redisLimit, Mockito.times(2)).limit();
    }

    @Test
    public void test2() {
        try {
            Mockito.when(redisLimit.limit()).thenReturn(false);
            commonAspect.before(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean limit = redisLimit.limit();
        System.out.println(limit);
        assertFalse(limit);
        Mockito.verify(redisLimit,Mockito.times(2)).limit();
    }
}
