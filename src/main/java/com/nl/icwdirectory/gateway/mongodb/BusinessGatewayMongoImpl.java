package com.nl.icwdirectory.gateway.mongodb;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.BusinessGateway;
import com.nl.icwdirectory.gateway.mongodb.repository.BusinessRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BusinessGatewayMongoImpl implements BusinessGateway {

    private final BusinessRepository businessRepository;

    public BusinessGatewayMongoImpl(BusinessRepository businessRepository) {
        this.businessRepository = businessRepository;
    }

    @Override
    public Business create(final Business businessToBeCreated) {
        Business insertedBusiness = businessRepository.insert(businessToBeCreated);
        log.info("Business successfully created: {}", insertedBusiness);
        return insertedBusiness;
    }
}
