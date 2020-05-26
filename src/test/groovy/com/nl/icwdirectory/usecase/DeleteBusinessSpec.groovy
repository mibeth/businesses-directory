package com.nl.icwdirectory.usecase

import com.nl.icwdirectory.domain.ErrorMessages
import com.nl.icwdirectory.gateway.BusinessGateway
import org.mockito.Mockito
import spock.lang.Specification

class DeleteBusinessSpec extends Specification{
    BusinessGateway businessGateway = Mock(BusinessGateway.class)
    DeleteBusiness deleteBusiness

    def setup() {
        deleteBusiness = new DeleteBusiness(businessGateway)
    }

    def "Should delete a business with valid id"() {
        given: "A business id to be deleted"
        def businessGateway = Mockito.mock(BusinessGateway.class)
        DeleteBusiness deleteBusiness = new DeleteBusiness(businessGateway)
        String businessId = UUID.randomUUID()

        when: "I try to delete the business"
        deleteBusiness.deleteById(businessId)

        then: "Verify BusinessGateway is called"
        Mockito.verify(businessGateway).delete(businessId)
    }

    def "Should throw Exception due to null id"() {
        given: "A null business id"
        String businessIdToBeDeleted = null

        when: "I try to delete the business"
        deleteBusiness.deleteById(businessIdToBeDeleted)

        then: "It should throw an exception"
        def e = thrown(IllegalArgumentException)
        e.message == ErrorMessages.INVALID_BUSINESS_ID.getMessage()
    }

    def "Should throw Exception due to empty id"() {
        given: "A empty business id"
        String businessIdToBeDeleted = ""

        when: "I try to delete the business"
        deleteBusiness.deleteById(businessIdToBeDeleted)

        then: "It should throw an exception"
        def e = thrown(IllegalArgumentException)
        e.message == ErrorMessages.INVALID_BUSINESS_ID.getMessage()
    }
}
