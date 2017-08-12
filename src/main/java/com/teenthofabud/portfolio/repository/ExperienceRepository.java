package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Experience;

public interface ExperienceRepository extends MongoRepository<Experience, String> {

}
