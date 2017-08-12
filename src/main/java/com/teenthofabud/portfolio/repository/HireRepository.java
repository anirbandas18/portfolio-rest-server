package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Hire;

public interface HireRepository extends MongoRepository<Hire, String> {

}
