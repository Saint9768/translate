package com.wind.demo.redis;

/**
 * @author Saint
 * @createTime 2020-05-21 20:18
 */
public class AccessKey implements KeyPrefix {

    /**
     * 超时时间
     */
    private int expireSeconds;

    /**
     * key前缀
     */
    private String prefix;

    /**
     * 设置key的过期时间和前缀
     * @param expireSeconds
     * @param prefix
     */
    public AccessKey(int expireSeconds, String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 用于设置翻译接口最大访问次数
     * @param expireSeconds
     * @return
     */
    public static AccessKey withExpire(int expireSeconds){
        return new AccessKey(expireSeconds, "access");
    }

    /**
     * 获取key的过期时间
     * @return
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }
}
