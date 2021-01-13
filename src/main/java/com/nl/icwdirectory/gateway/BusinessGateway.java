package com.nl.icwdirectory.gateway;

import com.nl.icwdirectory.domain.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BusinessGateway {

    void delete(String businessIdToBeDeleted);

    Business create(Business businessToBeCreated);

    Page<Business> getAllBusinesses(Pageable pageRequest);

    List<Business> createFromFile(List<Business> businesses);

    List<Business> getBusinessesByTagsAndName(String searchedText);

}
