package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Technology;

public interface TechnologyRepository extends MongoRepository<Technology, String> {

}
