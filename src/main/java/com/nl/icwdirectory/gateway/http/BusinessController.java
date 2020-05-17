package com.nl.icwdirectory.gateway.http;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.converter.BusinessToCreatedBusinessJson;
import com.nl.icwdirectory.gateway.http.converter.CreateBusinessJsonToBusiness;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import com.nl.icwdirectory.gateway.http.json.CreatedBusinessJson;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class BusinessController {

    private final CreateBusinessJsonToBusiness createBusinessJsonToBusiness;
    private final BusinessToCreatedBusinessJson businessToCreatedBusinessJson;
    private final CreateBusiness createBusiness;

    public BusinessController(
            CreateBusinessJsonToBusiness createBusinessJsonToBusiness,
            BusinessToCreatedBusinessJson businessToCreatedBusinessJson,
            CreateBusiness createBusiness) {
        this.createBusinessJsonToBusiness = createBusinessJsonToBusiness;
        this.businessToCreatedBusinessJson = businessToCreatedBusinessJson;
        this.createBusiness = createBusiness;
    }

    @ApiOperation(value = "Create a new Business")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Business successfully created"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping(value = URLMapping.CREATE_NEW_BUSINESS,
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedBusinessJson> createBusiness(
            @RequestBody @Valid final CreateBusinessJson createBusinessJson) {
        log.info("Creating user {}", createBusinessJson);
        final Business businessToBeCreated = createBusinessJsonToBusiness.convert(createBusinessJson);

        final Business businessCreated = createBusiness.createBusiness(businessToBeCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(businessToCreatedBusinessJson.convert(businessCreated));
    }
}
