package com.nl.icwdirectory.gateway.mongodb.repository;

import com.nl.icwdirectory.domain.Business;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusinessRepository extends MongoRepository<Business, String> {

}
