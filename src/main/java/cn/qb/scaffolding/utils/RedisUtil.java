package cn.qb.scaffolding.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    
    private static final String TOKEN_KEY_PREFIX = "user:token:";
    private static final long TOKEN_EXPIRE_HOURS = 24;

    /**
     * 存储用户token
     *
     * @param token 用户token
     * @param userId 用户ID
     */
    public void storeToken(String token, Integer userId) {
        try {
            String key = TOKEN_KEY_PREFIX + token;
            redisTemplate.opsForValue().set(key, userId.toString(), TOKEN_EXPIRE_HOURS, TimeUnit.HOURS);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis连接失败，无法存储token: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("存储token时发生错误: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 获取token对应的用户ID
     *
     * @param token 用户token
     * @return 用户ID
     */
    public Integer getUserIdByToken(String token) {
        try {
            String key = TOKEN_KEY_PREFIX + token;
            String userId = (String) redisTemplate.opsForValue().get(key);
            return userId != null ? Integer.parseInt(userId) : null;
        } catch (RedisConnectionFailureException e) {
            log.error("Redis连接失败，无法获取token: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("获取token时发生错误: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 删除token
     *
     * @param token 用户token
     */
    public void removeToken(String token) {
        try {
            String key = TOKEN_KEY_PREFIX + token;
            redisTemplate.delete(key);
        } catch (RedisConnectionFailureException e) {
            log.error("Redis连接失败，无法删除token: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("删除token时发生错误: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 检查token是否存在
     *
     * @param token 用户token
     * @return 是否存在
     */
    public boolean hasToken(String token) {
        try {
            String key = TOKEN_KEY_PREFIX + token;
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (RedisConnectionFailureException e) {
            log.error("Redis连接失败，无法检查token: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("检查token时发生错误: {}", e.getMessage());
            throw e;
        }
    }
} 