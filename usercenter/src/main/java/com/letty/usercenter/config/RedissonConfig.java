package com.letty.usercenter.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 */

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonConfig {

    private String port;
    private String url;
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        //1.创建配置
        Config config = new Config();
//        String redisAddress = "redis://127.0.0.1:6379";
        String redisAddress = String.format("redis://%s:%s",url,port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(3).setPassword(password);

        //2.创建redisson实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
