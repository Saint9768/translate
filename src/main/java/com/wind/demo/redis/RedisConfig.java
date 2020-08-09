package com.wind.demo.redis;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Redis参数的配置类
 * @author Saint
 * @createTime 2020-05-21 19:43
 */
@Configuration
@Component
@Data
@ToString
public class RedisConfig {
    @Value("${spring.redis.host:#{null}}")
    private String host;

    @Value("${spring.redis.port:#{null}}")
    private int port;

    /** 超时时间，单位：毫秒 */
    @Value("${spring.redis.timeout:#{null}}")
    private int timeout;

    @Value("${spring.redis.password:#{null}}")
    private String password;

    @Value("${spring.redis.jedis.pool.max-active:#{null}}")
    private int poolMaxTotal;

    @Value("${spring.redis.jedis.pool.max-idle:#{null}}")
    private int poolMaxIdle;

    @Value("${spring.redis.jedis.pool.max-wait:#{null}}")
    /** 连接池最大等待时间：毫秒 */
    private int poolMaxWait;
}
