package com.wzy.springboot.rest;

import com.wzy.springboot.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wzy
 */
public interface PersonsRepository extends JpaRepository<Person,Long> {
    public Person findByNameStartingWith(String name);
}
