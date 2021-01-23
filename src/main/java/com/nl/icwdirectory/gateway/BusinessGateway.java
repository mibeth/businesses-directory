package com.nl.icwdirectory.gateway;

import com.nl.icwdirectory.domain.Business;

import java.util.List;

public interface BusinessGateway {

    void delete(String businessIdToBeDeleted);

    Business create(Business businessToBeCreated);

    List<Business> getAllBusinesses();

    List<Business> createFromFile(List<Business> businesses);

    List<Business> getBusinessesByTagsAndName(String searchedText);

}
