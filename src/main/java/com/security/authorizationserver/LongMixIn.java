package com.security.authorizationserver;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LongMixIn {

    @JsonCreator
    public static Long fromString(Long id) {
        return Long.valueOf(id);
    }
}
