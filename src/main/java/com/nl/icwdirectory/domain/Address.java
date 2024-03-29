package com.nl.icwdirectory.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public final class Address {

    private String street;
    private String postalCode;
    private String city;

}
