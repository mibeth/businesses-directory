package com.nl.icwdirectory.usecase;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.BusinessGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchBusinesses {

    private final BusinessGateway businessGateway;

    public List<Business> getBusinessesByTagsAndName(final String searchCriteria) {
        log.info("Querying for all businesses on DB matching name or tag {}", searchCriteria);
        return businessGateway.getBusinessesByTagsAndName(searchCriteria);
    }

}
