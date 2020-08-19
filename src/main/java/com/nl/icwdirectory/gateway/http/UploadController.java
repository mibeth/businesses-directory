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
import org.springframework.ui.Model;
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
    private final CsvToJsonConverter csvToJsonConverter;
    private final JsonToBusinessConverter jsonToBusinessConverter;
    private final CreateBusiness createBusiness;

    @PostMapping(URLMapping.UPLOAD_CSV_FILE)
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        // validate file
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            // parse CSV file to create a list of `Business` objects
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                // create csv bean reader
                char SEMICOLON_DELIMETER = ';';
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

                // save businesses list on model
                model.addAttribute("businesses", businessJson);
                model.addAttribute("status", true);

            } catch (Exception ex) {
                model.addAttribute("message", "An error occurred while processing the CSV file.");
                model.addAttribute("status", false);
            }
        }

        return "file-upload-status";
    }

}
