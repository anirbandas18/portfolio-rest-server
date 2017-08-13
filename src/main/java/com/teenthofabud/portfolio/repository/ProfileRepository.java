package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {

}
