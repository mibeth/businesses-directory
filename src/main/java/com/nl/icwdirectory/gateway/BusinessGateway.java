package com.nl.icwdirectory.gateway;

import com.nl.icwdirectory.domain.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BusinessGateway {

  Business create(final Business businessToBeCreated);
  Page<Business> getAllBusinesses(final Pageable pageRequest);

}
