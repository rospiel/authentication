package com.security.authorizationserver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
@Validated
@ConfigurationProperties("auth")
public class AuthSecurityProperties {

    @NotBlank
    private String providerUrl;

    @NotNull
    private boolean opaqueToken;
}
