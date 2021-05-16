package com.quark.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lhr on 17-7-31.
 */
@SpringBootApplication
@EnableCaching//缓存支持
public class AdminApplication {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> containerCustomizer() {

        return (container -> {
            //自定义401页面
            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
            //自定义404页面
            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            //自定义500页面
            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
            //将错误页面加入到容器中
            container.addErrorPages(error401Page, error404Page, error500Page);
        });
    }

    public static void main(String[] args) throws IOException {
        //更改properties配置文件名称,避免依赖冲突
        Properties properties = new Properties();
        InputStream in = AdminApplication.class.getClassLoader().getResourceAsStream("admin.properties");
        properties.load(in);
        SpringApplication app = new SpringApplication(AdminApplication.class);
        app.setDefaultProperties(properties);
        app.run(args);
//        SpringApplication.run(CommonApplication.class, args);
    }
}
