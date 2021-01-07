package com.nl.icwdirectory.gateway.http;

import com.nl.icwdirectory.gateway.http.converter.CsvToJsonConverter;
import com.nl.icwdirectory.gateway.http.converter.JsonToBusinessConverter;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import com.nl.icwdirectory.gateway.http.json.CsvBusiness;
import com.nl.icwdirectory.gateway.http.mapping.URLMapping;
import com.nl.icwdirectory.usecase.CreateBusiness;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UploadController {
    private static final char SEMICOLON_DELIMETER = ';';
    private final CsvToJsonConverter csvToJsonConverter;
    private final JsonToBusinessConverter jsonToBusinessConverter;
    private final CreateBusiness createBusiness;

    @PostMapping(URLMapping.UPLOAD_CSV_FILE)
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {

        // validate file
        if (file.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The CSV file cannot be empty");
        } else {
            // parse CSV file to create a list of `Business` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                // create csv bean reader
                CsvToBean<CsvBusiness> csvToBean = new CsvToBeanBuilder<CsvBusiness>(reader)
                        .withSeparator(SEMICOLON_DELIMETER)
                        .withType(CsvBusiness.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withIgnoreQuotations(true)
                        .build();

                // convert `CsvToBean` object to list of businesses
                log.info("Converting CSV file to JavaBean");
                List<CsvBusiness> businesses = csvToBean.parse();
                List<CreateBusinessJson> businessJson =
                        businesses.stream().map(csvToJsonConverter::convert).collect(Collectors.toList());

                log.info("Saving csv file content to DB");
                createBusiness.createFromFile(
                        businessJson.stream().map(jsonToBusinessConverter::convert).collect(Collectors.toList()));
            } catch (Exception ex) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("An error occurred while processing the CSV file. ".concat(ex.getMessage()));
            }
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Businesses successfully created");
    }

}
