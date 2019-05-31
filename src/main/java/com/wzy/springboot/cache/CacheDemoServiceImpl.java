package com.wzy.springboot.cache;

import com.wzy.springboot.Person;
import com.wzy.springboot.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author wzy
 */
@Service
public class CacheDemoServiceImpl implements CacheDemoService {

    Logger logger = LoggerFactory.getLogger(CacheDemoServiceImpl.class);
    @Autowired
    private PersonRepository repository;

    //缓存新增的或更新的数据，缓存名为people，key为person的id
    @CachePut(value = "people", key = "#person.id")
    @Override
    public Person save(Person person) {
        Person p = repository.save(person);
        logger.info("为id、key为：" + person.getId() + "数据做了缓存");
        return p;
    }

    //从缓存people中删除key为id的数据
    @CacheEvict(value = "people")
    @Override
    public void remove(Long id) {
        logger.info("删除了id、key为" + id + "的数据缓存");
        repository.deleteById(id);
    }

    //缓存key为person.id的数据到缓存people中，不指定key，则将方法参数作为key
    @Cacheable(value = "people", key = "#person.id")
    @Override
    public Person findOne(Person person) {
        Long id = Long.valueOf(person.getId().longValue());
        Optional<Person> p = repository.findById(id);
        return p.get();
    }
}
