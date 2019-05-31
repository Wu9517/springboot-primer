package com.wzy.springboot.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzy
 */
@RestController
public class DataRedisController {

    @Autowired
    PersonRedisDao personRedisDao;

    //设置字符和对象
    @RequestMapping("/set")
    public void set() {
        PersonRedis personRedis = new PersonRedis("1", "wyf", 32);
        personRedisDao.save(personRedis);
        personRedisDao.stringRedisTemplateDemo();
    }

    //演示获得字符
    @RequestMapping("/getStr")
    public String getStr() {
        return personRedisDao.getString();
    }

    //演示获得对象
    @RequestMapping("/getPerson")
    public PersonRedis getPerson() {
        return personRedisDao.getPerson();
    }

}

