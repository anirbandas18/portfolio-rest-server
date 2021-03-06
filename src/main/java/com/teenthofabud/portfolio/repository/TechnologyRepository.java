package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teenthofabud.portfolio.model.collections.Technology;

@Repository
public interface TechnologyRepository extends MongoRepository<Technology, String> {

}
