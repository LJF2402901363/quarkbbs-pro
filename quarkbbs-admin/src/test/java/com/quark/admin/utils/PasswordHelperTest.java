package com.quark.admin.utils;

import com.quark.common.entity.AdminUser;
import org.junit.jupiter.api.Test;
/**
 * Classname:PasswordHelperTestt
 *
 * @description:
 * @author: 陌意随影
 * @Date: 2021-05-18 22:20
 * @Version: 1.0
 **/
//@SpringBootTest
public class PasswordHelperTest {
   @Test
    public void test(){
       AdminUser adminUser = new AdminUser();
       adminUser.setUsername("root");
       adminUser.setPassword("root");
       adminUser.setId(51);
       PasswordHelper.encryptPassword(adminUser);
       System.out.println(adminUser);
   }
}
