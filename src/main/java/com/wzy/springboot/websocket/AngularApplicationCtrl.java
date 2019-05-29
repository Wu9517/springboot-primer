package com.wzy.springboot.websocket;

import com.wzy.springboot.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wzy
 */
@RestController
public class AngularApplicationCtrl {

    @RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person search(String personName) {
        return new Person(personName, 12, "chengdu");
    }
}
