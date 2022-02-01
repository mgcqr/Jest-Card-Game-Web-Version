package com.mgcqr.jest.repository;

import com.mgcqr.jest.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisCacheRepository {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final String DEFAULT_KEY_PREFIX = "";
    private final int EXPIRE_TIME = 15;
    private final TimeUnit EXPIRE_TIME_TYPE = TimeUnit.MINUTES;




    /**
     * 数据缓存至redis
     *
     * @param key
     * @param value
     * @return
     */
    public <K, V> void set(K key, V value) {
        try {
            if (value != null) {
                redisTemplate
                        .opsForValue()
                        .set(DEFAULT_KEY_PREFIX + key, JsonUtil.toJsonString(value));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据缓存至redis失败");
        }
    }

    public <K, V> void setWithDefaultExpireTime(K key, V value){
        set(key, value, EXPIRE_TIME, EXPIRE_TIME_TYPE);
    }

    /**
     * 数据缓存至redis并设置过期时间
     *
     * @param key
     * @param value
     * @return
     */
    public <K, V> void set(K key, V value, long timeout, TimeUnit unit) {
        try {
            if (value != null) {
                redisTemplate
                        .opsForValue()
                        .set(DEFAULT_KEY_PREFIX + key, JsonUtil.toJsonString(value), timeout, unit);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据缓存至redis失败");
        }
    }

    /**
     * 写入 hash-set,已经是key-value的键值，不能再写入为hash-set
     * redisTemplate.opsForHash().put(key, subkey, value)中
     * subkey和value必须是String 这里用Jackson对value做序列化
     *
     * @param key    must not be {@literal null}.
     * @param subKey must not be {@literal null}.
     * @param value  写入的值
     */
    public <K, V> void setHashCache(K key, String subKey, V value) {
        try {
            redisTemplate.opsForHash().put(DEFAULT_KEY_PREFIX + key, subKey, JsonUtil.toJsonString(value));
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据缓存至redis失败");
        }
    }

    /**
     * 写入 hash-set,并设置过期时间
     * redisTemplate.opsForHash().put(key, subkey, value)中
     * subkey和value必须是String 这里用Jackson对value做序列化
     *
     * @param key    must not be {@literal null}.
     * @param subKey must not be {@literal null}.
     * @param value  写入的值
     */
    public <K, V> void setHashCache(K key, String subKey, V value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForHash().put(DEFAULT_KEY_PREFIX + key, subKey, JsonUtil.toJsonString(value));
            redisTemplate.expire(DEFAULT_KEY_PREFIX + key, timeout, unit);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException("数据缓存至redis失败");
        }
    }

    /**
     * 获取 hash-setvalue
     * subkey必须是String
     *
     * @param key    must not be {@literal null}.
     * @param subKey must not be {@literal null}.
     */
    public <K, V> V getHashCache(K key, String subKey, Class<V> valueType) {
        String jsonStr = (String) redisTemplate.opsForHash().get(DEFAULT_KEY_PREFIX + key, subKey);
        if(jsonStr == null) {
            return null;
        }
        try {
            return JsonUtil.toObject(jsonStr, valueType);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new RuntimeException("从redis缓存中获取缓存数据失败");
        }
    }


    /**
     * 从redis中获取缓存数据，转成对象
     *
     * @param key   must not be {@literal null}.
     * @param valueType 对象类型
     * @return
     */
    public <K, V> V getObject(K key, Class<V> valueType){
        String value = this.get(key);
        V result = null;
        if (!StringUtils.isEmpty(value)) {
            try {
                result = JsonUtil.toObject(value, valueType);
            }catch (Exception e){
                log.error(e.getMessage(), e);
                throw new RuntimeException("从redis缓存中获取缓存数据失败");
            }
        }
        return result;
    }

    /**
     * 从redis中获取缓存数据，转成list
     *
     * @param key   must not be {@literal null}.
     * @param valueType 对象类型
     * @return
     */
    public <K, V> List<V> getList(K key, Class<V> valueType) {
        String value = this.get(key);
        List<V> result = Collections.emptyList();
        if (!StringUtils.isEmpty(value)) {
            try {
                result = JsonUtil.toList(value, valueType);
            }catch (Exception e){
                log.error(e.getMessage(), e);
                throw new RuntimeException("从redis缓存中获取缓存数据失败");
            }
        }
        return result;
    }

    /**
     * 功能描述：Get the value of {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return java.lang.String
     * @date 2021/9/19
     **/
    public <K> String get(K key) {
        String value;
        try {
            value = redisTemplate.opsForValue().get(DEFAULT_KEY_PREFIX + key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("从redis缓存中获取缓存数据失败");
        }
        return value;
    }

    /**
     * 删除key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     */
    public void delete(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 序列化key
     */
    public byte[] dump(String key) {
        return redisTemplate.dump(key);
    }

    /**
     * 是否存在key
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 设置默认过期时间
     */
    public Boolean expireDefault(String key){
        return expire(key, EXPIRE_TIME, EXPIRE_TIME_TYPE);
    }

    /**
     * 设置过期时间
     */
    public Boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }


    /**
     * 移除 key 的过期时间，key 将持久保持
     */
    public Boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    /**
     * 返回 key 的剩余的过期时间
     */
    public Long getExpire(String key, TimeUnit unit) {
        return redisTemplate.getExpire(key, unit);
    }

    /**
     * 返回 key 的剩余的过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }
}
