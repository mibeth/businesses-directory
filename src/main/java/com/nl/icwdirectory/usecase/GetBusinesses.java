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
public class GetBusinesses {

    private final BusinessGateway businessGateway;

    public List<Business> getAllBusinesses() {
        log.info("Querying for all businesses on DB");
        return businessGateway.getAllBusinesses();
    }

}
