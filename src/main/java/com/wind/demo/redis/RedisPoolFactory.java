package com.wind.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jedis连接池的设置
 * redisPool要从RedisService中拿出来，否则会出现循环依赖问题。
 * @author Saint
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisConfig redisConfig;

    /**
     * JedisPool中的timeout单位是毫秒
     * @return
     */
    @Bean
    public JedisPool jedispoolfactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait());
        JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout(), redisConfig.getPassword(), 0);
        return jp;
    }

}