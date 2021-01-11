package com.nl.icwdirectory.gateway.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.icwdirectory.gateway.http.converter.CsvToJsonConverter;
import com.nl.icwdirectory.gateway.http.converter.JsonToBusinessConverter;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
final class UploadControllerTest {
    private CsvToJsonConverter csvToJsonConverter;
    private JsonToBusinessConverter jsonToBusinessConverter;
    private CreateBusiness createBusiness;
    private UploadController uploadController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
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
    void shouldReturnBadRequestDueToEmptyFile() throws Exception {
        // GIVEN a file with businesses to be created
        MockMultipartFile uploadedFile = new MockMultipartFile("empty.csv",
                "empty.csv", "text/csv",
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
