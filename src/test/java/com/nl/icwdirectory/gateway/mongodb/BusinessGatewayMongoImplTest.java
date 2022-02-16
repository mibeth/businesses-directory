package com.nl.icwdirectory.gateway.mongodb;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ComponentScan(basePackageClasses = BusinessGatewayMongoImpl.class)
final class BusinessGatewayMongoImplTest {

    @Autowired
    private BusinessGatewayMongoImpl businessGatewayMongoImpl;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        // Clean all data before each test
        mongoTemplate.findAllAndRemove(new Query(), Business.class);
    }

    @Test
    void shouldDeleteABusiness() {
        Business business = mongoTemplate.insert(buildSampleBusiness());
        List<Business> businessList = mongoTemplate.findAll(Business.class);
        assertEquals(1, businessList.size());
        businessGatewayMongoImpl.delete(business.getId());
        assertTrue(mongoTemplate.findAll(Business.class).isEmpty());
    }

    @Test
    void shouldCreateANewBusiness() {
        Business sampleTestingBusiness = buildSampleBusiness();
        Business result = businessGatewayMongoImpl.create(sampleTestingBusiness);

        assertEquals(sampleTestingBusiness, result);
        assertNotNull(result.getId());
    }

    @Test
    void shouldReturnEmptyResultWhenNoRecordsFound() {
        List<Business> allBusinesses = businessGatewayMongoImpl.getAllBusinesses();

        assertNotNull(allBusinesses);
        assertTrue(allBusinesses.isEmpty());
    }

    @Test
    void shouldReturnPagedResultOnRequest() {
        Business sampleTestingBusiness = buildSampleBusiness();
        mongoTemplate.insert(sampleTestingBusiness);

        List<Business> allBusinesses = businessGatewayMongoImpl.getAllBusinesses();

        assertNotNull(allBusinesses);
        assertEquals(1, allBusinesses.size());
        assertEquals("Granny's clothing", allBusinesses.get(0).getName());
    }

    @Test
    void shouldCreateBusinessesFromFile() {
        List<Business> sampleTestingBusiness = List.of(buildSampleBusiness());
        List<Business> result = businessGatewayMongoImpl.createFromFile(sampleTestingBusiness);

        assertEquals(sampleTestingBusiness, result);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldFindBusinessesByTag() {
        //Given a business exists on db and a tag to be searched
        final var sampleTestingBusiness = buildSampleBusiness();
        mongoTemplate.insert(sampleTestingBusiness);

        final var criteria = "tailoring";

        //When the search is performed
        final var result = businessGatewayMongoImpl.getBusinessesByTagsAndName(criteria);

        //Then the result is not empty
        assertFalse(result.isEmpty());
        assertTrue(result.get(0).getTags().contains(criteria));
    }

    private Business buildSampleBusiness() {
        return Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .address(Address.builder()
                        .city("Eindhoven").postalCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .images(Collections.singletonList("aUrl"))
                .description("The business purpose")
                .tags(List.of("clothing", "kleren", "tailoring"))
                .build();
    }

}
