package com.nl.icwdirectory.gateway.mongodb;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.BusinessGateway;
import com.nl.icwdirectory.gateway.mongodb.repository.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public final class BusinessGatewayMongoImpl implements BusinessGateway {

    private final BusinessRepository businessRepository;

    public BusinessGatewayMongoImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public void delete(String businessIdToBeDeleted) {
        businessRepository.deleteById(businessIdToBeDeleted);
        log.info("Executed delete operation, business id {}", businessIdToBeDeleted);
    }

    @Override
    public Business create(final Business businessToBeCreated) {
        Business insertedBusiness = businessRepository.insert(businessToBeCreated);
        log.info("Business successfully created: {}", insertedBusiness);
        return insertedBusiness;
    }

    @Override
    public Page<Business> getAllBusinesses(final Pageable pageable) {
        Page<Business> businesses = businessRepository.findAll(pageable);
        log.info("All businesses queried, total records found: {}", businesses.getTotalElements());
        return businesses;
    }
}
