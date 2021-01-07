package com.nl.icwdirectory.gateway.http.mapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class URLMapping {

    public static final String DELETE_BUSINESS = "/api/businesses/{id}";
    public static final String CREATE_NEW_BUSINESS = "/api/business";
    public static final String GET_BUSINESSES = "/api/businesses";
    public static final String UPLOAD_CSV_FILE = "/api/upload-csv-file";

}
