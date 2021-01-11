package com.nl.icwdirectory.gateway.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.converter.BusinessToJsonConverter;
import com.nl.icwdirectory.gateway.http.converter.JsonToBusinessConverter;
import com.nl.icwdirectory.gateway.http.json.BusinessJson;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import com.nl.icwdirectory.usecase.DeleteBusiness;
import com.nl.icwdirectory.usecase.GetBusinesses;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public final class BusinessControllerTest {

    private static final String PATH_MAPPING = "/directory";
    private JsonToBusinessConverter jsonToBusinessConverter;
    private BusinessToJsonConverter businessToJsonConverter;
    private DeleteBusiness deleteBusiness;
    private CreateBusiness createBusiness;
    private GetBusinesses getBusinesses;
    private BusinessController businessController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        jsonToBusinessConverter = mock(JsonToBusinessConverter.class);
        businessToJsonConverter = mock(BusinessToJsonConverter.class);
        deleteBusiness = mock(DeleteBusiness.class);
        createBusiness = mock(CreateBusiness.class);
        getBusinesses = mock(GetBusinesses.class);
        businessController = new BusinessController(
                jsonToBusinessConverter,
                businessToJsonConverter,
                deleteBusiness,
                createBusiness,
                getBusinesses);
        mockMvc = standaloneSetup(businessController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldDeleteBusinessById() throws Exception {
        // GIVEN a Business Id to be deleted
        String businessId = "5ec2f3cb71db2a7c13beb4fd";

        // WHEN I try to consume the endpoint to delete a business
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(PATH_MAPPING.concat(URLMapping.DELETE_BUSINESS), businessId)
                .contentType(APPLICATION_JSON))
                .andReturn();

        // THEN It should delete a business
        verify(deleteBusiness).deleteById(businessId);
        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    public void shouldCreateNewUser() throws Exception {
        // GIVEN a business to be created
        CreateBusinessJson businessToBeCreated = CreateBusinessJson.builder()
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

        when(businessToJsonConverter.convert(any(Business.class))).thenCallRealMethod();
        when(createBusiness.createBusiness(any())).thenReturn(createdBusiness);

        // WHEN I try to consume the endpoint to create a new user
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PATH_MAPPING.concat(URLMapping.CREATE_NEW_BUSINESS))
                .content(objectMapper.writeValueAsString(businessToBeCreated))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // THEN It should create a new user along with the generated id
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        Business businessFromResponse = objectMapper.readValue(responseBodyAsString, Business.class);

        assertEquals(createdBusiness, businessFromResponse);
        verify(createBusiness).createBusiness(any());
        verify(jsonToBusinessConverter).convert(any());
        verify(businessToJsonConverter).convert(any(Business.class));
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
        mockMvc.perform(post(PATH_MAPPING.concat(URLMapping.CREATE_NEW_BUSINESS))
                .content(objectMapper.writeValueAsString(businessToBeCreated))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        verify(createBusiness, times(0)).createBusiness(any());
        verify(jsonToBusinessConverter, times(0)).convert(any());
        verify(businessToJsonConverter, times(0)).convert(any(Business.class));
    }

    @Test
    public void shouldValidateIfURLChanged() {
        // If the URL changes it could break clients. This test will inform that in case of URL changes
        String expected = "/directory/business";
        String actual = PATH_MAPPING.concat(URLMapping.CREATE_NEW_BUSINESS);
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyResultWhenNoRecordsFound() throws Exception {
        ReflectionTestUtils.setField(businessController, "elementsPerPage", 6);

        when(getBusinesses.getAllBusinesses(any())).thenReturn(Page.empty());
        when(businessToJsonConverter.convert(anyList())).thenCallRealMethod();

        MvcResult mvcResult = mockMvc.perform(get(PATH_MAPPING.concat(URLMapping.GET_BUSINESSES))
                .queryParam("pageNumber", String.valueOf(0))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult.getResponse());
        assertEquals("[]", mvcResult.getResponse().getContentAsString());
        verify(getBusinesses).getAllBusinesses(any());
        verify(businessToJsonConverter).convert(anyList());
    }

    @Test
    public void shouldReturnPagedResultOnRequest() throws Exception {
        ReflectionTestUtils.setField(businessController, "elementsPerPage", 6);

        PageImpl<Business> result = new PageImpl<>(List.of(Business.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("0629795318")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .id("b973713a-c71d-437a-a65a-e38d23471e4b")
                .build()));

        List<BusinessJson> expectedResult = List.of(BusinessJson.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .email("klerengekste@gmail.com")
                .phone("0629795318")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .id("b973713a-c71d-437a-a65a-e38d23471e4b")
                .build());

        when(getBusinesses.getAllBusinesses(any(PageRequest.class))).thenReturn(result);
        when(businessToJsonConverter.convert(any(Business.class))).thenCallRealMethod();
        when(businessToJsonConverter.convert(anyList())).thenCallRealMethod();

        MvcResult mvcResult = mockMvc.perform(get(PATH_MAPPING.concat(URLMapping.GET_BUSINESSES))
                .param("pageNumber", String.valueOf(0))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        // THEN It should return a result list with values in it
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        List<BusinessJson> businessFromResponse = objectMapper.readValue(responseBodyAsString, new TypeReference<>() {});

        assertNotNull(mvcResult.getResponse());
        assertEquals(expectedResult, businessFromResponse);
        verify(getBusinesses).getAllBusinesses(any());
        verify(businessToJsonConverter).convert(anyList());
    }
}
