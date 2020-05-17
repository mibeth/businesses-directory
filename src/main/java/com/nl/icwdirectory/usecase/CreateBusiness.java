package com.nl.icwdirectory.usecase;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.domain.exception.InvalidPhoneException;
import com.nl.icwdirectory.gateway.BusinessGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
public class CreateBusiness {

    public static final String PHONE_CANT_BE_NULL = "The phone number can't be null";
    private final BusinessGateway businessGateway;

    public CreateBusiness(final BusinessGateway businessGateway) {
        this.businessGateway = businessGateway;
    }

    public Business createBusiness(final Business businessToBeCreated) {
        validateBusiness(businessToBeCreated);
        log.info("Creating business {}", businessToBeCreated);
        return businessGateway.create(businessToBeCreated);
    }

    private void validateBusiness(final Business businessToBeCreated) {
        notNull(businessToBeCreated, "The business object must be defined");
        notNull(businessToBeCreated.getName(), "The Business name must be defined");
        notNull(businessToBeCreated.getOwnerFirstName(), "The Business owner name must be defined");
        notNull(businessToBeCreated.getEmail(), "The Business email must be defined");
        if (businessToBeCreated.getPhone() == null) {
            throw new InvalidPhoneException(PHONE_CANT_BE_NULL);
        }
    }
}
