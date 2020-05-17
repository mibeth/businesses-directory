package com.nl.icwdirectory.gateway.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.converter.BusinessToCreatedBusinessJson;
import com.nl.icwdirectory.gateway.http.converter.CreateBusinessJsonToBusiness;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class BusinessControllerTest {

    private CreateBusinessJsonToBusiness createBusinessJsonToBusiness;
    private BusinessToCreatedBusinessJson businessToCreatedBusinessJson;
    private CreateBusiness createBusiness;
    private BusinessController businessController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        createBusinessJsonToBusiness = mock(CreateBusinessJsonToBusiness.class);
        businessToCreatedBusinessJson = mock(BusinessToCreatedBusinessJson.class);
        createBusiness = mock(CreateBusiness.class);
        businessController = new BusinessController(
                createBusinessJsonToBusiness,
                businessToCreatedBusinessJson,
                createBusiness);
        mockMvc = standaloneSetup(businessController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        // GIVEN a business to be created
        Business businessToBeCreated = Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("0629795318")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .build();
        Business createdBusiness = Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("0629795318")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .id(UUID.randomUUID().toString())
                .build();

        when(createBusinessJsonToBusiness.convert(any())).thenCallRealMethod();
        when(businessToCreatedBusinessJson.convert(any())).thenCallRealMethod();
        when(createBusiness.createBusiness(any())).thenReturn(createdBusiness);

        // WHEN I try to consume the endpoint to create a new user
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URLMapping.CREATE_NEW_BUSINESS)
                .content(objectMapper.writeValueAsString(businessToBeCreated))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // THEN It should create a new user along with the generated id
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Business businessFromResponse = objectMapper.readValue(responseBodyAsString, Business.class);

        assertEquals(createdBusiness, businessFromResponse);
        verify(createBusiness, times(1)).createBusiness(any());
        verify(createBusinessJsonToBusiness, times(1)).convert(any());
        verify(businessToCreatedBusinessJson, times(1)).convert(any());
    }

    @Test
    public void shouldReturnBadRequestDueToMissingParameters() throws Exception {
        // GIVEN a user to be created
        Business businessToBeCreated = Business.builder()
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
                .build();

        // WHEN I try to consume the endpoint to create a new user
        mockMvc.perform(post(URLMapping.CREATE_NEW_BUSINESS)
                .content(objectMapper.writeValueAsString(businessToBeCreated))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(createBusiness, times(0)).createBusiness(any());
        verify(createBusinessJsonToBusiness, times(0)).convert(any());
        verify(businessToCreatedBusinessJson, times(0)).convert(any());
    }

    @Test
    public void shouldValidateIfURLChanged() {
        // If the URL changes it could break clients. This test will inform that in case of URL changes
        String expected = "/api/business";
        String actual = URLMapping.CREATE_NEW_BUSINESS;
        assertEquals(expected, actual);
    }
}
