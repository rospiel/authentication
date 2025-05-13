package com.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.security.authorizationserver.AuthSecurityProperties;
import com.security.authorizationserver.JwtKeyStoreProperties;
import com.security.authorizationserver.LongMixIn;
import com.security.entity.Group;
import com.security.entity.Permission;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.jackson2.CoreJackson2Module;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        //http.csrf(AbstractHttpConfigurer::disable);
        http.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                              .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.authorizeHttpRequests(auth ->
                auth.requestMatchers(HttpMethod.POST, "/user/v1").permitAll()
                    .requestMatchers(HttpMethod.POST, "/oauth2/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/csrf").permitAll()
                    .requestMatchers( "/customer/v1").permitAll()
                    .requestMatchers( "/error").permitAll()
                    .anyRequest().authenticated())
            .with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());


        return http.formLogin(Customizer.withDefaults()).build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder,
                                                                 Environment environment,
                                                                 JdbcTemplate jdbcTemplate) {

        return new JdbcRegisteredClientRepository(jdbcTemplate);
    }

    @Bean
    public OAuth2AuthorizationService auth2AuthorizationService(JdbcOperations jdbcOperations,
                                                                RegisteredClientRepository registeredClientRepository,
                                                                ObjectMapper objectMapper) {
        JdbcOAuth2AuthorizationService oAuth2AuthorizationService = new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
        JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper rowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(registeredClientRepository);

        rowMapper.setObjectMapper(objectMapper);
        oAuth2AuthorizationService.setAuthorizationRowMapper(rowMapper);
        return oAuth2AuthorizationService;
    }

    @Bean
    public OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService(JdbcOperations jdbcOperations,
                                                                               RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings(AuthSecurityProperties properties) {
        return AuthorizationServerSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public JWKSet jWKSet(JwtKeyStoreProperties properties) throws Exception {
        final char[] keyStorePass = properties.getPassword().toCharArray();
        final String keypairAlias = properties.getKeypairAlias();

        final Resource jksLocation = properties.getJksLocation();
        final InputStream inputStream = jksLocation.getInputStream();
        final KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        final RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

        return new JWKSet(rsaKey);
    }

    @Bean
    public JWKSource<SecurityContext> jWKSource(JWKSet jwkSet) {
        return (((jwkSelector, securityContext) -> jwkSelector.select(jwkSet)));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtEncodingContextOAuth2TokenCustomizer(UserRepository userRepository,
                                                                                             UserService userService){
        return (context -> {
            Authentication authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User) {
                final User user = (User) authentication.getPrincipal();

                userService.searchBy(user.getUsername()).ifPresentOrElse(
                        (founded -> {
                            Set<String> authorities = new HashSet<>();
                            for (Group group : founded.getGroups()) {
                                authorities.addAll(group.getPermissions().stream()
                                                                         .map(Permission::getName)
                                                                         .map(String::toString)
                                                                         .collect(Collectors.toList()));
                            }
                            context.getClaims().claim("user_id", founded.getId());
                            context.getClaims().claim("user_fullname", founded.getName());
                            context.getClaims().claim("authorities", authorities);
                        }),
                        () -> {
                            throw new UsernameNotFoundException("User not found with e-mail provided");
                        }
                );


            }

        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(new CoreJackson2Module());
        objectMapper.registerModules(SecurityJackson2Modules.getModules(JdbcOAuth2AuthorizationService.class.getClassLoader()));
        objectMapper.registerModule(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.addMixIn(Long.class, LongMixIn.class);
        return objectMapper;
    }
}
