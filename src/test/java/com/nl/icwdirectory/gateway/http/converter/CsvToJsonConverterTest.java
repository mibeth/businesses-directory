package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import com.nl.icwdirectory.gateway.http.json.CsvBusiness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class CsvToJsonConverterTest {

    private CsvToJsonConverter csvToJsonConverter;

    @BeforeEach
    void setupTest() {
        csvToJsonConverter = new CsvToJsonConverter();
    }

    @Test
    void shouldConvertCreateUserJsonToUser() {
        CsvBusiness businessToConvert = CsvBusiness.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .city("Eindhoven")
                .postCode("5618ZW")
                .street("Bouteslaan 123")
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .images("aValue")
                .description("The business purpose")
                .tags("clothing,kleren")
                .build();

        CreateBusinessJson expectedResult = CreateBusinessJson.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .address(Address.builder()
                        .city("Eindhoven").postalCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .images(List.of("aValue"))
                .description("The business purpose")
                .tags(List.of("clothing", "kleren"))
                .build();

        CreateBusinessJson convertedUser = csvToJsonConverter.convert(businessToConvert);

        assertThat(convertedUser).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldConvertCreateUserJsonToUserWithEmptyValues() {
        CsvBusiness businessToConvert = CsvBusiness.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .city("Eindhoven")
                .postCode("5618ZW")
                .street("Bouteslaan 123")
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .description("The business purpose")
                .build();

        CreateBusinessJson expectedResult = CreateBusinessJson.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .address(Address.builder()
                        .city("Eindhoven").postalCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .images(Collections.emptyList())
                .description("The business purpose")
                .tags(Collections.emptyList())
                .build();

        CreateBusinessJson convertedUser = csvToJsonConverter.convert(businessToConvert);

        assertThat(convertedUser).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}