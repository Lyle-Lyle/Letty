package com.letty.usercenter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * knife4j configuration
 */
@Configuration
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("user-api")         // 分组名称
                .pathsToMatch("/user/**")  // 接口请求路径规则
                .build();
    }
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Knife4j标题")
                        .description("Knife4j说明")
                        .version("v1")
                        .contact(new Contact().name("Lyle").email("Xiongjinyue.lyle@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                );

    }
}

