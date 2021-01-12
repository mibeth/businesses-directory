package com.nl.icwdirectory.usecase;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.domain.exception.BusinessValidationException;
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
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    private static final Pattern VALID_EMAIL = Pattern.compile(EMAIL_PATTERN);
    private final BusinessGateway businessGateway;

    public Business createBusiness(final Business businessToBeCreated) {
        validateBusiness(businessToBeCreated);
        log.info("Creating business {}", businessToBeCreated);
        return businessGateway.create(businessToBeCreated);
    }

    public List<Business> createFromFile(final List<Business> businesses) {
        log.info("Creating businesses from file");
        validateBusinesses(businesses);
        return businessGateway.createFromFile(businesses);
    }

    private void validateBusinesses(List<Business> businesses) {
        BusinessValidationException throwableError = null;
        for (int i = 0; i < businesses.size(); i++) {
            final var businessForCreation = businesses.get(i);
            try {
                validateBusiness(businessForCreation);
            } catch (BusinessValidationException | IllegalArgumentException e) {
                if (throwableError == null) {
                    throwableError = new BusinessValidationException("There were validation errors found when checking the file's content");
                    throwableError.addSuppressed(new BusinessValidationException(e.getMessage().concat(" on line ".concat(String.valueOf(i+1)))));
                } else {
                    throwableError.addSuppressed(new BusinessValidationException(e.getMessage().concat(" on line ".concat(String.valueOf(i+1)))));
                }
            }
        }

        if (throwableError != null) {
            throw throwableError;
        }
    }

    private void validateBusiness(final Business businessToBeCreated) {
        notNull(businessToBeCreated, "The business object must be defined");
        notNull(businessToBeCreated.getName(), "The Business name must be defined");
        notNull(businessToBeCreated.getOwnerFirstName(), "The Business owner name must be defined");
        notNull(businessToBeCreated.getEmail(), "The Business email must be defined");
        notNull(businessToBeCreated.getPhone(), "The Business phone number must be defined");

        if (!VALID_EMAIL.matcher(businessToBeCreated.getEmail().replace(" ", "")).matches()) {
            throw new BusinessValidationException("The email format is invalid");
        }

        if (!VALID_PHONE.matcher(businessToBeCreated.getPhone().replace(" ", "")).matches()) {
            throw new BusinessValidationException("The phone number format is invalid");
        }
    }

}
