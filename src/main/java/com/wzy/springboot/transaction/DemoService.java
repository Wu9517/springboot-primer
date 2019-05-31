package com.wzy.springboot.transaction;

import com.wzy.springboot.Person;

/**
 * @author wzy
 */
public interface DemoService {
    public Person savePersonWithRollBack(Person person);

    public Person savePersonWithoutRollBack(Person person);
}
