package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Employee;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
