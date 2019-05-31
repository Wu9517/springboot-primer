package com.wzy.springboot.cache;

import com.wzy.springboot.Person;

/**
 * @author wzy
 */
public interface CacheDemoService {
    public Person save(Person person);

    public void remove(Long id);

    public Person findOne(Person person);

}
