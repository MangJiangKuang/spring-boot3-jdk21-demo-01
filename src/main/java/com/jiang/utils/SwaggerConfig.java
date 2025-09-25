package com.jiang.utils;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger2的接口配置
 *
 * @author Do_LaLi
 */
@Configuration

public class SwaggerConfig {
    /**
     * 是否开启swagger
     */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /**
     * 设置api文档的基本信息
     *
     * @return OpenAPI对象
     */
    @Bean
    public OpenAPI baseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("用户系统 API")
                        .description("用户/角色管理接口文档")
                        .version("1.0.0")
                        .contact(new Contact().name("Team").email("team@example.com"))
                        .license(new License().name("Apache 2.0")))
                .servers(List.of(new Server().url("http://localhost:8080").description("本地环境")))
                .components(new Components()
                        .addSecuritySchemes("BearerToken",
                                new SecurityScheme()
                                        // 描述使用 Bearer Token 进行身份验证
                                        .description("JWT授权(数据格式: Bearer <token>)")
                                        // 设置安全方案类型为 HTTP，使用 Bearer 认证方案，令牌格式为 JWT
                                        .type(SecurityScheme.Type.HTTP)

                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("BearerToken"));
    }
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("private")
                .pathsToMatch("/private/**")
                .build();
    }

}
