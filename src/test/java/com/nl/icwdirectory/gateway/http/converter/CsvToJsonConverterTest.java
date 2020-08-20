package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import com.nl.icwdirectory.gateway.http.json.CsvBusiness;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class CsvToJsonConverterTest {

    private CsvToJsonConverter csvToJsonConverter;

    @Before
    public void setupTest() {
        csvToJsonConverter = new CsvToJsonConverter();
    }

    @Test
    public void shouldConvertCreateUserJsonToUser() {
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
                .images("")
                .description("The business purpose")
                .tags("clothing,kleren")
                .build();

        CreateBusinessJson expectedResult = CreateBusinessJson.builder()
                .name("Granny's clothing")
                .ownerFirstName("Satan")
                .ownerLastName("Lucifer")
                .address(Address.builder()
                        .city("Eindhoven").postCode("5618ZW").street("Bouteslaan 123")
                        .build())
                .email("klerengekste@gmail.com")
                .website("www.customclothing.nl")
                .phone("+316666666")
                .logo("aUrl")
                .images(Collections.emptyList())
                .description("The business purpose")
                .tags(List.of("clothing", "kleren"))
                .build();

        CreateBusinessJson convertedUser = csvToJsonConverter.convert(businessToConvert);

        assertThat(convertedUser).usingRecursiveComparison().isEqualTo(expectedResult);
    }
}