package com.nl.icwdirectory.gateway.http.mapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class URLMapping {

    public static final String DELETE_BUSINESS = "/businesses/{id}";
    public static final String CREATE_NEW_BUSINESS = "/business";
    public static final String GET_BUSINESSES = "/businesses";
    public static final String UPLOAD_CSV_FILE = "/upload-csv-file";
    public static final String SEARCH_BUSINESSES = "/businesses/{criteria}";

}
