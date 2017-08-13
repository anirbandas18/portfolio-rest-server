package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Institution;

public interface InstitutionRepository extends MongoRepository<Institution, String> {

}
