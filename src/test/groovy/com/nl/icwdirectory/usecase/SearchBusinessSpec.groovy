package com.nl.icwdirectory.usecase

import com.nl.icwdirectory.domain.Address
import com.nl.icwdirectory.domain.Business
import com.nl.icwdirectory.gateway.BusinessGateway
import spock.lang.Specification

class SearchBusinessSpec extends Specification {
    BusinessGateway businessGateway = Mock(BusinessGateway.class)
    SearchBusinesses searchBusiness

    def setup() {
        searchBusiness = new SearchBusinesses(businessGateway)
    }

    def "Check that returns a list when querying by tag on DB"() {
        given: "A tag for searching"
        String criteria = "tailoring"

        and: "a result is generated"
        businessGateway.getBusinessesByTagsAndName(criteria) >> {
            Collections.singletonList(
                    Business.builder()
                            .name("Granny's clothing")
                            .ownerFirstName("Satan")
                            .ownerLastName("Lucifer")
                            .address(Address.builder()
                                    .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                                    .build())
                            .email("klerengekste@gmail.com")
                            .website("www.customclothing.nl")
                            .logo("aUrl")
                            .images(Collections.singletonList("aUrl"))
                            .description("The business purpose")
                            .tags(List.of("clothing", "kleren", "tailoring"))
                            .build())
        }

        when: "the search is performed"
        List<Business> result = searchBusiness.getBusinessesByTagsAndName(criteria)

        then: "then should return http OK Response with a result"
        result != null
        result.size() > 0
        result.get(0).tags.contains(criteria)
    }

}
