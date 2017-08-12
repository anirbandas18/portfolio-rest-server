package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Introduction;

public interface IntroductionRepository extends MongoRepository<Introduction, String> {

}
