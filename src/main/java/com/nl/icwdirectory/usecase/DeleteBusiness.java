package com.nl.icwdirectory.usecase;


import com.nl.icwdirectory.domain.ErrorMessages;
import com.nl.icwdirectory.gateway.BusinessGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class DeleteBusiness {

    private final BusinessGateway businessGateway;

    public DeleteBusiness(final BusinessGateway businessGateway) {this.businessGateway = businessGateway; }

    public void deleteById(final String businessToBeDeleted) {
        validateBusinessId(businessToBeDeleted);
        businessGateway.delete(businessToBeDeleted);
    }

    private void validateBusinessId(String businessToBeDeleted) {
        if(StringUtils.isEmpty(businessToBeDeleted)) throw new IllegalArgumentException(ErrorMessages.INVALID_BUSINESS_ID.getMessage());
    }
}