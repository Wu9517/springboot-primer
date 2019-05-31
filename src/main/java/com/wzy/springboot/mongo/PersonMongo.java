package com.wzy.springboot.mongo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author wzy
 */
@Document  //映射领域模型和MongoDB文档
public class PersonMongo {

    @Id //该属性为文档id
    private String id;
    private String name;
    private Integer age;
    @Field("locs") //注解此属性在文档中的名称为locs,locations将以数组形式存在当前数据库记录中
    private Collection<LocationMongo> locations = new LinkedHashSet<>();

    public PersonMongo(String name, Integer age) {
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

    public Collection<LocationMongo> getLocations() {
        return locations;
    }

    public void setLocations(Collection<LocationMongo> locations) {
        this.locations = locations;
    }
}
