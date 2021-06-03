package com.quark.porent;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import java.io.IOException;

/**
 * @Author LHR
 * Create By 2017/8/21
 */
@SpringBootApplication(exclude= {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class}
)
public class PortalApplication {


    public static void main(String[] args) throws IOException {
        //更改properties配置文件名称,避免依赖冲突
        SpringApplication.run(PortalApplication.class,args);
    }

}
