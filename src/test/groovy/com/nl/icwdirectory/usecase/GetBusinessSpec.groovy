package com.nl.icwdirectory.usecase

import com.nl.icwdirectory.domain.Address
import com.nl.icwdirectory.domain.Business
import com.nl.icwdirectory.gateway.BusinessGateway
import spock.lang.Specification

class GetBusinessSpec extends Specification {
    BusinessGateway businessGateway = Mock(BusinessGateway.class)
    GetBusinesses getBusinesses

    def setup() {
        getBusinesses = new GetBusinesses(businessGateway)
    }

    def "Check that returns empty result when querying all elements on DB"() {
        given: "No parameters"

        and: "a result is generated"
        businessGateway.getAllBusinesses() >> {
            Collections.emptyList()
        }

        when: "requests for all elements on DB"
        List<Business> result = getBusinesses.getAllBusinesses()

        then: "then should return http OK Response with empty result"
        result != null
    }

    def "Check that returns a list when querying all elements on DB"() {
        given: "No parameters"

        and: "a result is generated"
        businessGateway.getAllBusinesses() >> {
            Collections.singletonList(
                    Business.builder()
                            .name("Granny's clothing")
                            .ownerFirstName("Satan")
                            .ownerLastName("Lucifer")
                            .address(Address.builder()
                                    .city("Eindhoven").postalCode("5618ZW").street("Bouteslaan 123")
                                    .build())
                            .email("klerengekste@gmail.com")
                            .website("www.customclothing.nl")
                            .logo("aUrl")
                            .images(Collections.singletonList("aUrl"))
                            .description("The business purpose")
                            .tags(List.of("clothing", "kleren"))
                            .build())
        }

        when: "requests for all elements on DB"
        List<Business> result = getBusinesses.getAllBusinesses()

        then: "then should return http OK Response with result"
        result != null
        result.size() == 1
        result.get(0).name == "Granny's clothing"
    }
}
