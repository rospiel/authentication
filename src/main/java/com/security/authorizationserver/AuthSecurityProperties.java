package com.security.authorizationserver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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
