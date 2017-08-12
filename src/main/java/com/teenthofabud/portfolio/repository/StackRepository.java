package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Stack;

public interface StackRepository extends MongoRepository<Stack, String> {

}
