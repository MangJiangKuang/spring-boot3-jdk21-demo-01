package com.jiang;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration.class,
        org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration.class,
        org.apache.shiro.spring.config.web.autoconfigure.ShiroWebFilterConfiguration.class
})
// 开启Swagger
public class JiangApplication {

    public static void main(String[] args) {
        System.out.println("Starting Jiang Application...");
        org.springframework.boot.SpringApplication.run(JiangApplication.class, args);
        System.out.println("Jiang Application started successfully.");
    }
}
