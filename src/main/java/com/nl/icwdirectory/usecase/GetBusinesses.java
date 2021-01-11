package com.nl.icwdirectory.usecase;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.BusinessGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetBusinesses {

    private final BusinessGateway businessGateway;

    public Page<Business> getAllBusinesses(final Pageable pageable) {
        log.info("Querying for all businesses on DB, page number: {} ", pageable.getPageNumber());
        return businessGateway.getAllBusinesses(pageable);
    }
}
