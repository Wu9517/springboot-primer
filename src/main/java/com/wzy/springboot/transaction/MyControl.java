package com.wzy.springboot.transaction;

import com.wzy.springboot.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzy
 */
@RestController
public class MyControl {

    @Autowired
    private DemoService demoService;

    @RequestMapping(value = "/rollback")
    public Person rollback(Person person) {
        return demoService.savePersonWithRollBack(person);
    }

    @RequestMapping(value = "noRollback")
    public Person noRollback(Person person) {
        return demoService.savePersonWithoutRollBack(person);
    }


}
