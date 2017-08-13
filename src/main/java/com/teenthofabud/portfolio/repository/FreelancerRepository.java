package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.teenthofabud.portfolio.model.collections.Freelancer;

@Repository
public interface FreelancerRepository extends MongoRepository<Freelancer, String> {
	
	public Freelancer findById(String id);
	
	public Freelancer findByEmailId(String emailId);
	
	public Freelancer findByPhoneNumber(String phoneNumber);
	
	public Freelancer findByFirstNameAndLastName(String firstName, String lastName);

}
