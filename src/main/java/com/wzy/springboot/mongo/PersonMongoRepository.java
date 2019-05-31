package com.wzy.springboot.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author wzy
 */
public interface PersonMongoRepository extends MongoRepository<PersonMongo, String> {

    PersonMongo findByName(String name);

    @Query("{'age':?0}")
    List<PersonMongo> withQueryFindByAge(Integer age);
}
