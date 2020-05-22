package com.nl.icwdirectory.gateway.http.json;

import com.nl.icwdirectory.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@AllArgsConstructor
@Value
@Builder
public class BusinessJson {

    String id;
    String name;
    String ownerFirstName;
    String ownerLastName;
    Address address;
    String email;
    String website;
    String phone;
    String logo;
    List<String> images;
    String description;
    List<String> tags;

}
