package com.nl.icwdirectory.usecase;


import com.nl.icwdirectory.domain.ErrorMessages;
import com.nl.icwdirectory.gateway.BusinessGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteBusiness {

    private final BusinessGateway businessGateway;

    public void deleteById(final String businessToBeDeleted) {
        validateBusinessId(businessToBeDeleted);
        businessGateway.delete(businessToBeDeleted);
    }

    private void validateBusinessId(String businessToBeDeleted) {
        if (isEmpty(businessToBeDeleted))
            throw new IllegalArgumentException(ErrorMessages.INVALID_BUSINESS_ID.getMessage());
    }

}
