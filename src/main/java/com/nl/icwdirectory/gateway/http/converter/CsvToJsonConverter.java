package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Address;
import com.nl.icwdirectory.gateway.http.json.CreateBusinessJson;
import com.nl.icwdirectory.gateway.http.json.CsvBusiness;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CsvToJsonConverter implements Converter<CsvBusiness, CreateBusinessJson> {

    @Override
    public CreateBusinessJson convert(final CsvBusiness source) {
        return CreateBusinessJson.builder()
                .name(source.getName())
                .ownerFirstName(source.getOwnerFirstName())
                .ownerLastName(source.getOwnerLastName())
                .address(Address.builder()
                        .street(source.getStreet())
                        .postalCode(source.getPostCode())
                        .city(source.getCity())
                        .build())
                .email(source.getEmail())
                .website(source.getWebsite())
                .phone(source.getPhone())
                .logo(source.getLogo())
                .images(StringUtils.isNotEmpty(source.getImages()) ?
                        List.of(source.getImages().split(",")) :
                        Collections.emptyList())
                .description(source.getDescription())
                .tags(StringUtils.isNotEmpty(source.getTags()) ?
                        List.of(source.getTags().split(",")) :
                        Collections.emptyList())
                .build();
    }

}
