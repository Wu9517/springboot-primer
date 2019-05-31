package com.wzy.springboot.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 需在docker中启动mongo容器生效
 *
 * @author wzy
 */
@RestController
@RequestMapping(value = "/mongo")
public class DataMongoController {

    @Autowired
    PersonMongoRepository repository;

    @RequestMapping(value = "/save")
    public PersonMongo save() {
        PersonMongo personMongo = new PersonMongo("wyf", 32);
        Collection<LocationMongo> locations = new LinkedHashSet<>();
        LocationMongo location1 = new LocationMongo("上海", "2009");
        LocationMongo location2 = new LocationMongo("合肥", "2010");
        LocationMongo location3 = new LocationMongo("广州", "2011");
        LocationMongo location4 = new LocationMongo("马鞍山", "2012");
        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        locations.add(location4);
        personMongo.setLocations(locations);
        return repository.save(personMongo);
    }

    @RequestMapping("/q1")
    public PersonMongo q1(String name) {
        return repository.findByName(name);
    }

    @RequestMapping("/q2")
    public List<PersonMongo> q2(Integer age) {
        return repository.withQueryFindByAge(age);
    }

}
