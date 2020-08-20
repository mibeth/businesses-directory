package com.nl.icwdirectory.gateway.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.converter.CsvToJsonConverter;
import com.nl.icwdirectory.gateway.http.converter.JsonToBusinessConverter;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import org.apache.commons.compress.utils.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest {
    private CsvToJsonConverter csvToJsonConverter;
    private JsonToBusinessConverter jsonToBusinessConverter;
    private CreateBusiness createBusiness;
    private UploadController uploadController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        jsonToBusinessConverter = mock(JsonToBusinessConverter.class);
        csvToJsonConverter = mock(CsvToJsonConverter.class);
        createBusiness = mock(CreateBusiness.class);
        uploadController = new UploadController(
                csvToJsonConverter,
                jsonToBusinessConverter,
                createBusiness);
        mockMvc = standaloneSetup(uploadController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnBadRequestDueToEmptyFile() throws Exception {
        // GIVEN a file with businesses to be created
        MockMultipartFile uploadedFile = new MockMultipartFile("empty.csv",
                "empty.csv", "text/plain",
                //UploadController.class.getClassLoader().getResourceAsStream("empty.csv"));
                "".getBytes());

        // WHEN I try to consume the endpoint to create a new user
        mockMvc.perform(MockMvcRequestBuilders.multipart(URLMapping.UPLOAD_CSV_FILE)
                .file(uploadedFile))
                .andExpect(status().is(400));

        verifyNoInteractions(createBusiness);
        verifyNoInteractions(jsonToBusinessConverter);
        verifyNoInteractions(csvToJsonConverter);
    }
}
