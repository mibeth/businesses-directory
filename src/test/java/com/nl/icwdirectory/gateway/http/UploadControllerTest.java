package com.nl.icwdirectory.gateway.http;

import com.nl.icwdirectory.gateway.http.converter.CsvToJsonConverter;
import com.nl.icwdirectory.gateway.http.converter.JsonToBusinessConverter;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static util.FileReaderUtil.readFile;

@ExtendWith(MockitoExtension.class)
final class UploadControllerTest {
    private CsvToJsonConverter csvToJsonConverter;
    private JsonToBusinessConverter jsonToBusinessConverter;
    private CreateBusiness createBusiness;
    private UploadController uploadController;
    private MockMvc mockMvc;

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
    }

    @Test
    void shouldReturnBadRequestDueToEmptyFile() throws Exception {
        // GIVEN a file with businesses to be created
        final var uploadedFile = new MockMultipartFile("file",
                "empty.csv", "text/csv",
                "".getBytes());

        // WHEN I try to consume the endpoint to upload a file
        final var mvcResult = mockMvc.perform(MockMvcRequestBuilders.multipart(URLMapping.UPLOAD_CSV_FILE)
                .file(uploadedFile))
                .andExpect(status().is(400))
                .andReturn();

        assertEquals("The CSV file cannot be empty", mvcResult.getResponse().getContentAsString());
        verifyNoInteractions(createBusiness);
        verifyNoInteractions(jsonToBusinessConverter);
        verifyNoInteractions(csvToJsonConverter);
    }

    @Test
    void shouldWorkWithProperFile() throws Exception {
        // GIVEN a file with businesses to be created
        final var testingFile = readFile("mycsv.csv");
        final var uploadedFile = new MockMultipartFile("file",
                "empty.csv", "text/csv",
                testingFile.getBytes());

        when(createBusiness.createFromFile(anyList())).thenReturn(Collections.emptyList());
        when(jsonToBusinessConverter.convert(any())).thenCallRealMethod();
        when(csvToJsonConverter.convert(any())).thenCallRealMethod();

        // WHEN I try to consume the endpoint to upload a file
        final var result = mockMvc.perform(MockMvcRequestBuilders.multipart(URLMapping.UPLOAD_CSV_FILE)
                .file(uploadedFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andReturn();

        assertNotNull(result);
        assertEquals("Businesses successfully created", result.getResponse().getContentAsString());
        verify(createBusiness, only()).createFromFile(anyList());
        verify(jsonToBusinessConverter, only()).convert(any());
        verify(csvToJsonConverter, only()).convert(any());
    }

    @Test
    void shouldNotifyWhenAnErrorIsThrown() throws Exception {
        // GIVEN a file with businesses to be created
        final var uploadedFile = new MockMultipartFile("file",
                "empty.csv", "text/csv",
                "data".getBytes());

        when(createBusiness.createFromFile(anyList())).thenThrow(new RuntimeException("Oops!"));

        // WHEN I try to consume the endpoint to upload a file
        final var result = mockMvc.perform(MockMvcRequestBuilders.multipart(URLMapping.UPLOAD_CSV_FILE)
                .file(uploadedFile)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().is(400))
                .andReturn();

        assertNotNull(result);
        assertEquals("An error occurred while processing the CSV file.", result.getResponse().getContentAsString());
        verify(createBusiness, only()).createFromFile(anyList());
        verifyNoInteractions(jsonToBusinessConverter);
        verifyNoInteractions(csvToJsonConverter);
    }

}
