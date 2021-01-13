package com.nl.icwdirectory.usecase

import com.nl.icwdirectory.domain.Address
import com.nl.icwdirectory.domain.Business
import com.nl.icwdirectory.domain.exception.BusinessValidationException
import com.nl.icwdirectory.gateway.BusinessGateway
import spock.lang.Specification

class CreateBusinessSpec extends Specification {
    BusinessGateway businessGateway = Mock(BusinessGateway.class)
    CreateBusiness createBusiness

    def setup() {
        createBusiness = new CreateBusiness(businessGateway)
    }

    def "test create business"() {
        given: "A new business to be created"
        def businessToBeCreated = Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("+31629795318")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .build()
        println "businessToBeCreated = $businessToBeCreated"

        and: "a random UUID identifying the business id created"
        def randomUUID = UUID.randomUUID().toString()
        businessGateway.create(businessToBeCreated) >> {
            Business.builder()
                    .name("Granny's clothing")
                    .ownerFirstName("Satan")
                    .email("klerengekste@gmail.com")
                    .phone("0629795318")
                    .address(Address.builder()
                            .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                            .build())
                    .id(randomUUID)
                    .build()
        }

        when: "I try to create the business"
        Business createdbusiness = createBusiness.createBusiness(businessToBeCreated)

        then: "The business should be created successfully"
        createdbusiness != null
        createdbusiness.getName() == businessToBeCreated.getName()
        createdbusiness.getEmail() == businessToBeCreated.getEmail()
        createdbusiness.getId() == randomUUID
    }

    def "Should throw Exception due to null object"() {
        given: "Given an invalid null business"
        Business businessToBeCreated = null

        when: "I try to create the business"
        createBusiness.createBusiness(businessToBeCreated)

        then: "It should throw an exception"
        def e = thrown(IllegalArgumentException)
        e.message == "The business object must be defined"
    }

    def "Check if phone format is invalid to throw exception"() {
        given: "a business with all required data"
        Business businessToBeCreated = Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .phone("(+31)6435678")
                .build()

        when: "try to create an business"
        createBusiness.createBusiness(businessToBeCreated)

        then: "an Exception should be thrown"
        def exception = thrown(BusinessValidationException)
        exception.message == "The phone number format is invalid"
    }

    def "This test verifies that phone is not null"() {
        given: "Given a business to be created"
        Business businessToBeCreated = Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .build()

        when: "when tries to create it"
        createBusiness.createBusiness(businessToBeCreated)

        then: "then should throw an exception"
        def exception = thrown(IllegalArgumentException)
        exception.message == "The Business phone number must be defined"
    }

    def "test create business from file"() {
        given: "A list of businesses to be created"
        def businessToBeCreated = List.of(Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("0629795318")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .build())
        println "businessToBeCreated = $businessToBeCreated"

        and: "a random UUID identifying the business id created"
        def randomUUID = UUID.randomUUID().toString()
        businessGateway.createFromFile(businessToBeCreated) >> {
            Collections.singletonList(Business.builder()
                    .name("Granny's clothing")
                    .ownerFirstName("Satan")
                    .email("klerengekste@gmail.com")
                    .phone("0629795318")
                    .address(Address.builder()
                            .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                            .build())
                    .id(randomUUID)
                    .build())
        }

        when: "I try to create the business with a list"
        List<Business> createdbusiness = createBusiness.createFromFile(businessToBeCreated)

        then: "The business should be created successfully"
        createdbusiness != null
        createdbusiness.get(0).getName().equals(businessToBeCreated.get(0).getName())
        createdbusiness.get(0).getId().equals(randomUUID)
    }

    def "test create business from file with validation error"() {
        given: "A list of businesses to be created"
        def businessToBeCreated = List.of(Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("(+31)680235")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .build(),
                Business.builder()
                        .name("GrandGranny's clothing")
                        .ownerFirstName("Say10")
                        .email("klerengeksteAtgmail.com")
                        .phone("0629795318")
                        .address(Address.builder()
                                .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                                .build())
                        .build())
        println "businessToBeCreated = $businessToBeCreated"

        and: "a random UUID identifying the business id created"
        def randomUUID = UUID.randomUUID().toString()
        businessGateway.createFromFile(businessToBeCreated) >> {
            List.of(Business.builder()
                    .name("Granny's clothing")
                    .ownerFirstName("Satan")
                    .email("klerengekste@gmail.com")
                    .phone("0629795318")
                    .address(Address.builder()
                            .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                            .build())
                    .id(randomUUID)
                    .build())
        }

        when: "I try to create the business with a list"
        List<Business> createdbusiness = createBusiness.createFromFile(businessToBeCreated)

        then: "then should throw an exception"
        def exception = thrown(BusinessValidationException)
        exception.message == "There were validation errors found when checking the file's content"
        exception.getSuppressed().size() > 0
    }

}
