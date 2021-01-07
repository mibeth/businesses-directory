package com.nl.icwdirectory.gateway.http.json;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public final class CsvBusiness {
    @CsvBindByName(column = "business_name")
    private String name;
    @CsvBindByName(column = "owner_first_name")
    private String ownerFirstName;
    @CsvBindByName(column = "owner_last_name")
    private String ownerLastName;
    @CsvBindByName
    private String street;
    @CsvBindByName
    private String postCode;
    @CsvBindByName
    private String city;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String website;
    @CsvBindByName
    @NotNull
    private String phone;
    @CsvBindByName
    private String logo;
    @CsvBindByName
    private String images;
    @CsvBindByName
    private String description;
    @CsvBindByName
    private String tags;

}
