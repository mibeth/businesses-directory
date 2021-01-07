package com.nl.icwdirectory.gateway.mongodb;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
@ComponentScan(basePackageClasses = BusinessGatewayMongoImpl.class)
public final class BusinessGatewayMongoImplTest {

    @Autowired
    private BusinessGatewayMongoImpl businessGatewayMongoImpl;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void setUp() {
        // Clean all data before each test
        mongoTemplate.findAllAndRemove(new Query(), Business.class);
    }

    @Test
    public void shouldDeleteABusiness() {
        Business business = mongoTemplate.insert(buildSampleBusiness());
        List<Business> businessList = mongoTemplate.findAll(Business.class);
        assertEquals(1, businessList.size());
        businessGatewayMongoImpl.delete(business.getId());
        assertTrue(mongoTemplate.findAll(Business.class).isEmpty());
    }

    @Test
    public void shouldCreateANewBusiness() {
        Business sampleTestingBusiness = buildSampleBusiness();
        Business result = businessGatewayMongoImpl.create(sampleTestingBusiness);

        assertEquals(sampleTestingBusiness, result);
        assertNotNull(result.getId());
    }

    @Test
    public void shouldReturnEmptyResultWhenNoRecordsFound() {
        Page<Business> allBusinesses = businessGatewayMongoImpl.getAllBusinesses(
                PageRequest.of(0, 6, Sort.Direction.ASC, "business_name")
        );

        assertNotNull(allBusinesses);
        assertTrue(allBusinesses.isEmpty());
    }

    @Test
    public void shouldReturnPagedResultOnRequest() {
        Business sampleTestingBusiness = buildSampleBusiness();
        mongoTemplate.insert(sampleTestingBusiness);

        Page<Business> allBusinesses = businessGatewayMongoImpl.getAllBusinesses(
                PageRequest.of(0, 6, Sort.Direction.ASC, "business_name")
        );

        assertNotNull(allBusinesses);
        assertEquals(1, allBusinesses.getTotalElements());
        assertEquals("Granny's clothing", allBusinesses.getContent().get(0).getName());
    }

    @Test
    public void shouldCreateBusinessesFromFile() {
        List<Business> sampleTestingBusiness = List.of(buildSampleBusiness());
        List<Business> result = businessGatewayMongoImpl.createFromFile(sampleTestingBusiness);

        assertEquals(sampleTestingBusiness, result);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    private Business buildSampleBusiness() {
        return Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .images(Collections.singletonList("aUrl"))
                .description("The business purpose")
                .tags(List.of("clothing", "kleren"))
                .build();
    }
}
