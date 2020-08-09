package com.wind.demo.redis;

/**
 * Redis中的key前缀
 * @author Saint
 */
public interface KeyPrefix {

    /**
     * 有效时间
     * @return
     */
    int expireSeconds();

    /**
     * key的前缀
     * @return
     */
    String getPrefix();
}
