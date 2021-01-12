package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

final class JsonToBusinessConverterTest {

    private JsonToBusinessConverter jsonToBusinessConverter;

    @BeforeEach
    void setupTest() {
        jsonToBusinessConverter = new JsonToBusinessConverter();
    }

    @Test
    void shouldConvertCreateUserJsonToUser() {
        CreateBusinessJson businessToConvert = CreateBusinessJson.builder()
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
                .images(Collections.singletonList("aUrl"))
                .description("The business purpose")
                .tags(List.of("clothing", "kleren"))
                .build();

        Business result = jsonToBusinessConverter.convert(businessToConvert);

        assertThat(result).usingRecursiveComparison().ignoringFields("id").isEqualTo(businessToConvert);
    }
}
