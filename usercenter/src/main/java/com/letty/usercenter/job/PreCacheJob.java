package com.letty.usercenter.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


// 重点用户

@Component
@Slf4j
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    // 重点用户
    private List<Long> mainUserList = Arrays.asList(1L);
    // 每天执行，预热推荐用户
    @Scheduled(cron = "0 4 0 * * *")
    public void doCacheRecommendUser() {
        RLock lock = redissonClient.getLock("letty:user:precachejob:docache:lock");
        try {
            // 如果获取到锁
            if (lock.tryLock(0, 30000L, TimeUnit.MINUTES)) {
                for (Long userId: mainUserList) {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
                    Page<User> userPage = userService.page(new Page<>(1,20), queryWrapper);
                    String redisKey = String.format("letty:user:recommend:%s", userId);
                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
                    // 写缓存
                    try {
                        valueOperations.set(redisKey, userPage,10, TimeUnit.MINUTES);
                    } catch (Exception e) {
                        log.error("redis set key error", e);
                    }
                }


            }
        } catch (InterruptedException e) {
            log.error("doCacheRecommendUser error", e);
        } finally {
            //只能释放自己的锁
            // 防止前面代码中断执行放到finally中
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

    }
}
