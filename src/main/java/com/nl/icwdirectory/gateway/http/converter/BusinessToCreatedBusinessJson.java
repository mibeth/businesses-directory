package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.json.CreatedBusinessJson;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BusinessToCreatedBusinessJson implements Converter<Business, CreatedBusinessJson> {

    @Override
    public CreatedBusinessJson convert(final Business source) {
        return CreatedBusinessJson.builder()
                .id(source.getId())
                .name(source.getName())
                .ownerFirstName(source.getOwnerFirstName())
                .ownerLastName(source.getOwnerLastName())
                .address(source.getAddress())
                .email(source.getEmail())
                .website(source.getWebsite())
                .phone(source.getPhone())
                .logo(source.getLogo())
                .images(source.getImages())
                .description(source.getDescription())
                .tags(source.getTags())
                .build();
    }
}
