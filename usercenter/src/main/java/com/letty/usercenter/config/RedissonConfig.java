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
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedissonConfig {

    private String port;
    private String host;

    @Bean
    public RedissonClient redissonConfig() {
        //1.创建配置
        Config config = new Config();
//        String redisAddress = "redis://127.0.0.1:6379";
        String redisAddress = String.format("redis://%s:%s",host,port);
        config.useSingleServer().setAddress(redisAddress).setDatabase(3);

        //2.创建redisson实例
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
