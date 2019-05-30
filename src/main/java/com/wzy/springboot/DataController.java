package com.wzy.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wzy
 */
@RestController
public class DataController {

    private static final Logger log = LoggerFactory.getLogger(DataController.class);

    @Autowired
    PersonRepository personRepository;


    /***
     * 保存
     * @param name
     * @param address
     * @param age
     * @return
     */
    @RequestMapping(value = "/save")
    public Person save(String name, String address, Integer age) {
        Person p = personRepository.save(new Person(name, age, address));
        log.info(p.getAddress());
        return p;
    }

    ;

    @RequestMapping(value = "/q1")
    public List<Person> q1(String address) {
        List<Person> persons = personRepository.findByAddress(address);
        return persons;
    }

    @RequestMapping(value = "/q2")
    public Person q2(String name, String address) {
        Person person = personRepository.findByNameAndAddress(name, address);
        return person;
    }

    @RequestMapping(value = "/q3")
    public Person q3(String name, String address) {
        Person person = personRepository.withNameAndAddressQuery(name, address);
        return person;
    }

    @RequestMapping(value = "/q4")
    public List<Person> q4(String name, String address) {
        List<Person> peoples = personRepository.withNameAndAddressNamedQuery(name, address);
        return peoples;
    }

    /***
     * 测试排序
     * @return
     */
    @RequestMapping(value = "/sort")
    public List<Person> sort() {
        List<Person> peoples = personRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        return peoples;
    }

    /***
     * 测试分页
     * @return
     */
    @RequestMapping(value = "/page")
    public Page<Person> page() {
        Page<Person> pagePeople = personRepository.findAll(new PageRequest(1, 2));
        return pagePeople;
    }

}
