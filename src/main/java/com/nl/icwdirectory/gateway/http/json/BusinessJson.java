package com.nl.icwdirectory.gateway.http.json;

import com.nl.icwdirectory.domain.Address;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
public class BusinessJson {

    private String id;
    private String name;
    private String ownerFirstName;
    private String ownerLastName;
    private Address address;
    private String email;
    private String website;
    private String phone;
    private String logo;
    private List<String> images;
    private String description;
    private List<String> tags;

}
