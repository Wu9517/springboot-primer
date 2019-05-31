package com.wzy.springboot.transaction;

import com.wzy.springboot.Person;
import com.wzy.springboot.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wzy
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private PersonRepository repository;

    @Transactional(rollbackFor = {IllegalArgumentException.class})
    @Override
    public Person savePersonWithRollBack(Person person) {
        Person person1 = repository.save(person);
        if (person.getName().equals("王云飞")) {
            throw new IllegalArgumentException("王云飞已存在，数据将回滚！");
        }
        return person1;
    }

    @Transactional(noRollbackFor = {IllegalArgumentException.class})
    @Override
    public Person savePersonWithoutRollBack(Person person) {
        Person person1 = repository.save(person);
        if (person.getName().equals("王云飞")) {
            throw new IllegalArgumentException("王云飞虽已存在，但数据不会回滚！");
        }
        return person1;
    }
}
