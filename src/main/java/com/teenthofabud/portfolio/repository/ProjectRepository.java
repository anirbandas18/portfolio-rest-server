package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

}
