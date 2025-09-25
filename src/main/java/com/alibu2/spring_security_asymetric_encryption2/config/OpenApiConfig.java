package com.alibu2.spring_security_asymetric_encryption2.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring Security JWT Asymmetric Demo",
                        url = "https://example.com"
                ),
                description = "OpenApi documentation for Spring Security Project",
                title = "Open API Specification",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://example/license"
                ),
                termsOfService = "https://example/terms"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local ENV"
                ),
                @Server(
                        description = "Pro ENV",
                        url = "https://prod.example"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)

@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth decription",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
