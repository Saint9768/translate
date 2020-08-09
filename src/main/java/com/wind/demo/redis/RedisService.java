package com.wind.demo.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Saint
 * @createTime 2020-05-21 20:12
 */
@Service
public class RedisService {

    /** 通过JedisPool来获取Jedis对象 */
    @Autowired
    JedisPool jedisPool;

    /**
     * 获取单个对象
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 设置对象
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(KeyPrefix prefix, String key, T value){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //将value的类型转换为string
            String str = beanToString(value);
            //如果value为空，则返回false
            if(str == null || str.length() <= 0) {
                return false;
            }
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            //获取过期时间
            int seconds = prefix.expireSeconds();
            //如果我们设置的过期时间是0，那么redis就是调用Jedis连接池中的永不过期的方法
            if(seconds <=0){
                jedis.set(realKey, str);
            }else{
                jedis.setex(realKey,seconds,str);
            }

            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 判断key是否存在
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean exists(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.exists(realKey);
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 删除一个指定前缀为prefix的key
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> boolean delete(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            long res = jedis.del(realKey);
            return res > 0;
        }finally{
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long incr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 这是一个原子操作
     * 减少值，相应key的value值减少1，如果value是字符串或者别的就返回-1.
     * @param prefix
     * @param key
     * @param <T>
     * @return
     */
    public <T> Long decr(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            //生成真正的key
            String realKey = prefix.getPrefix() + key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 泛型转化为String类型的方法
     * @param value
     * @param <T>
     * @return
     */
    public <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        //如果value不为空，判断value的类型
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class){
            return "" + value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
            return "" + value;
        }else{
            return JSON.toJSONString(value);
        }

    }

    /**
     * String类型转泛型
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        //判断clazz的类型，并将str转换为clazz的类型
        if(clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        }else if(clazz == String.class){
            return (T) str;
        }else if(clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }else{
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    /**
     * 关闭Jedis连接池
     * @param jedis
     */
    private void returnToPool(Jedis jedis) {
        if(jedis != null){
            //用连接池的话就返回到连接池。不是关闭。
            jedis.close();
        }
    }
}
