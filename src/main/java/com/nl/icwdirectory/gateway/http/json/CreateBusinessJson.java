package com.nl.icwdirectory.gateway.http.json;

import com.nl.icwdirectory.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class CreateBusinessJson {

    @NotNull
    private String name;
    @NotNull
    private String ownerFirstName;
    private String ownerLastName;
    private Address address;
    @NotNull
    private String email;
    private String website;
    @NotNull
    private String phone;
    private String logo;
    private List<String> images;
    private String description;
    private List<String> tags;

}
