package com.nl.icwdirectory.usecase;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.domain.exception.InvalidPhoneException;
import com.nl.icwdirectory.gateway.BusinessGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.util.Assert.notNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateBusiness {

    private static final String PHONE_PATTERNS
            = "^\\d{10}$" //Ten digit number
            + "|^(\\+\\d{1,2})?\\d{9}$"; //Starting by +
    private static final Pattern VALID_PHONE = Pattern.compile(PHONE_PATTERNS);
    private final BusinessGateway businessGateway;

    public Business createBusiness(final Business businessToBeCreated) {
        validateBusiness(businessToBeCreated);
        log.info("Creating business {}", businessToBeCreated);
        return businessGateway.create(businessToBeCreated);
    }

    public List<Business> createFromFile(final List<Business> businesses) {
        log.info("Creating businesses from file");
        return businessGateway.createFromFile(businesses);
    }

    private void validateBusiness(final Business businessToBeCreated) {
        notNull(businessToBeCreated, "The business object must be defined");
        notNull(businessToBeCreated.getName(), "The Business name must be defined");
        notNull(businessToBeCreated.getOwnerFirstName(), "The Business owner name must be defined");
        notNull(businessToBeCreated.getEmail(), "The Business email must be defined");
        notNull(businessToBeCreated.getPhone(), "The Business phone number must be defined");

        if (!VALID_PHONE.matcher(businessToBeCreated.getPhone().replace(" ", "")).matches()) {
            throw new InvalidPhoneException("The phone number format is invalid");
        }
    }

}
