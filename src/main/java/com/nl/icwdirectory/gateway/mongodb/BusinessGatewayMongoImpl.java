package com.nl.icwdirectory.gateway.mongodb;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.BusinessGateway;
import com.nl.icwdirectory.gateway.mongodb.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public final class BusinessGatewayMongoImpl implements BusinessGateway {

    private final BusinessRepository businessRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void delete(final String businessIdToBeDeleted) {
        businessRepository.deleteById(businessIdToBeDeleted);
        log.info("Executed delete operation, business id {}", businessIdToBeDeleted);
    }

    @Override
    public Business create(final Business businessToBeCreated) {
        Business insertedBusiness = businessRepository.save(businessToBeCreated);
        log.info("Business successfully created: {}", insertedBusiness);
        return insertedBusiness;
    }

    @Override
    public List<Business> createFromFile(final List<Business> businessToBeCreated) {
        List<Business> insertedBusiness = businessRepository.saveAll(businessToBeCreated);
        log.info("Businesses successfully created: {}", insertedBusiness);
        return insertedBusiness;
    }

    @Override
    public List<Business> getAllBusinesses() {
        List<Business> businesses = businessRepository.findAll();
        log.info("All businesses queried, total records found: {}", businesses.size());
        return businesses;
    }

    @Override
    public List<Business> getBusinessesByTagsAndName(String searchedText) {
        final var textQuery = TextQuery.queryText(new TextCriteria().matchingAny(searchedText)).sortByScore();
        return mongoTemplate.find(textQuery, Business.class, "businesses");
    }

    @PostConstruct
    private void createIndexes() {
        final var textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("name")
                .onField("tags")
                .build();
        mongoTemplate.indexOps(Business.class).ensureIndex(textIndex);
    }
}
