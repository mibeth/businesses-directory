package com.nl.icwdirectory.gateway.http.converter;

import com.nl.icwdirectory.domain.Business;
import com.nl.icwdirectory.gateway.http.json.BusinessJson;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BusinessToJsonConverter implements Converter<Business, BusinessJson> {

    @Override
    public BusinessJson convert(final Business source) {
        return BusinessJson.builder()
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

    public List<BusinessJson> convert(List<Business> businesses) {
        return businesses.stream().map(this::convert).collect(Collectors.toUnmodifiableList());
    }
}
