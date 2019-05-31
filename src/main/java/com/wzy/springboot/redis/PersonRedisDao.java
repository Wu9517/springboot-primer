package com.wzy.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author wzy
 */
@Repository
public class PersonRedisDao {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //注入基于字符串的简单属性操作方法
    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> valPosStr;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    //注入基于对象的简单属性操作方法
    @Resource(name = "redisTemplate")
    ValueOperations<Object, Object> valOps;

    public void stringRedisTemplateDemo() {
        valPosStr.set("xx", "yy");
    }

    public void save(PersonRedis personRedis) {
        valOps.set(personRedis.getId(), personRedis);
    }

    public String getString() {
        return valPosStr.get("xx");
    }

    public PersonRedis getPerson() {
        return (PersonRedis) valOps.get("1");
    }


}
