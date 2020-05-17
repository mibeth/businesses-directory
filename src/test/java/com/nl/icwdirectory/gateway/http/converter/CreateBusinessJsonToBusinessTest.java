package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class CreateBusinessJsonToBusinessTest {

    private CreateBusinessJsonToBusiness createBusinessJsonToBusiness;

    @Before
    public void setupTest() {
        createBusinessJsonToBusiness = new CreateBusinessJsonToBusiness();
    }

    @Test
    public void shouldConvertCreateUserJsonToUser() {
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

        Business result = createBusinessJsonToBusiness.convert(businessToConvert);
        EqualsBuilder.reflectionEquals(businessToConvert, result);
    }
}
