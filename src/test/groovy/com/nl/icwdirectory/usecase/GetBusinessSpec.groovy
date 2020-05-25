package com.nl.icwdirectory.usecase

import com.nl.icwdirectory.domain.Address
import com.nl.icwdirectory.domain.Business
import com.nl.icwdirectory.gateway.BusinessGateway
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import spock.lang.Specification

class GetBusinessSpec extends Specification {
    BusinessGateway businessGateway = Mock(BusinessGateway.class)
    GetBusinesses getBusinesses

    def setup() {
        getBusinesses = new GetBusinesses(businessGateway)
    }

    def "Check that returns empty result when querying all elements on DB"() {
        given: "Page 0 is requested"
        def pageable = PageRequest.of(0, 6, Sort.Direction.ASC, "business_name")

        and: "a result is generated"
        businessGateway.getAllBusinesses(pageable) >> {
            Page.empty()
        }

        when: "requests for all elements on DB"
        Page<Business> result = getBusinesses.getAllBusinesses(pageable)

        then: "then should return http OK Response with empty result"
        result != null
    }

    def "Check that returns a list when querying all elements on DB"() {
        given: "Page 0 is requested"
        def pageable = PageRequest.of(0, 6, Sort.Direction.ASC, "business_name")

        and: "a result is generated"
        businessGateway.getAllBusinesses(pageable) >> {
            new PageImpl<Business>(List.of(
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
                            .tags(List.of("clothing", "kleren"))
                            .build()))
        }

        when: "requests for all elements on DB"
        Page<Business> result = getBusinesses.getAllBusinesses(pageable)

        then: "then should return http OK Response with result"
        result != null
        result.totalElements == 1
        result.getContent().get(0).name == "Granny's clothing"
    }
}
