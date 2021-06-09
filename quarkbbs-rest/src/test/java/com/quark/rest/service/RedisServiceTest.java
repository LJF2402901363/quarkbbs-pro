package com.quark.rest.service;

import com.quark.common.entity.User;
import com.quark.common.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
/**
 * Classname:RedisServiceTest
 *
 * @description:
 * @author: 陌意随影
 * @Date: 2021-05-29 20:05
 * @Version: 1.0
 **/
@SpringBootTest
public class RedisServiceTest {
    @Autowired
    private RedisService redisService;
    @Test
    public void testRedis(){
        List<User> list = new ArrayList<>();
        list.add(new User());
         redisService.setCacheList("name",list);
        List<User> userList = redisService.getCacheList("name");
        userList.forEach(user -> {
            System.out.println(user);
        });
    }
}
