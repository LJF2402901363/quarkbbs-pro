package com.quark.admin.dao;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.crazycake.shiro.RedisManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Classname:SessionDaoTest
 *
 * @description:
 * @author: 陌意随影
 * @Date: 2021-05-21 21:44
 * @Version: 1.0
 **/
@SpringBootTest
public class SessionDaoTest {
    @Autowired
    SessionDAO sessionDAO;
    @Autowired
    RedisManager redisManager;
   @Test
    public void testSessionDao(){
       String host = redisManager.getHost();
       System.out.println(host);
//       Session root = sessionDAO.readSession("root");
//        System.out.println(root);
    }
}
