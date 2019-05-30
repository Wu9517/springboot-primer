package com.wzy.springboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author wzy
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByAddress(String address);

    Person findByNameAndAddress(String name, String address);

    @Query("select p from Person p where p.name=:name and p.address=:address")
    Person withNameAndAddressQuery(@Param("name") String name,
                                   @Param("address") String address);

    //这里对应Person类里的@NamedQuery
    List<Person> withNameAndAddressNamedQuery(String name, String address);
}
