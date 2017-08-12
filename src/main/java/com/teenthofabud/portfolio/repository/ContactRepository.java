package com.teenthofabud.portfolio.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.teenthofabud.portfolio.model.collections.Contact;

public interface ContactRepository extends MongoRepository<Contact, String> {

}
