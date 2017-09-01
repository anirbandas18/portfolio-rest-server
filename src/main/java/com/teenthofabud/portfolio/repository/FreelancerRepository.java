package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.teenthofabud.portfolio.model.collections.Freelancer;

@Repository
public interface FreelancerRepository extends MongoRepository<Freelancer, String>, QueryByExampleExecutor<Freelancer> {

}
