package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teenthofabud.portfolio.model.collections.Hire;

@Repository
public interface HireRepository extends MongoRepository<Hire, String> {

}
