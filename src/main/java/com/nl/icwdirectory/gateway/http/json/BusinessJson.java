package com.nl.icwdirectory.gateway.http.json;

import com.nl.icwdirectory.domain.Address;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class BusinessJson {

    private final String id;
    private final String name;
    private final String ownerFirstName;
    private final String ownerLastName;
    private final Address address;
    private final String email;
    private final String website;
    private final String phone;
    private final String logo;
    private final List<String> images;
    private final String description;
    private final List<String> tags;

}
