package com.wzy.springboot.redis;

import java.io.Serializable;

/**
 * 必须序列化，使用Jackson做序列化需要一个空构造
 * * @author wzy
 */
public class PersonRedis implements Serializable {

    private String id;
    private String name;
    private Integer age;

    public PersonRedis() {
    }

    public PersonRedis(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
