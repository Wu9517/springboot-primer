package com.wzy.springboot.cache;

import com.wzy.springboot.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzy
 */
@RestController
public class CacheController {

    @Autowired
    private CacheDemoService cacheDemoService;

    @RequestMapping("/put")
    public Person put(Person person) {
        return cacheDemoService.save(person);
    }

    @RequestMapping("/able")
    public Person cacheable(Person person) {
        return cacheDemoService.findOne(person);
    }

    @RequestMapping("/evit")
    public String evit(Long id) {
        cacheDemoService.remove(id);
        return "ok";
    }

}
