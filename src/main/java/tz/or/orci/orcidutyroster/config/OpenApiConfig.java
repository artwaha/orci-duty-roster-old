package tz.or.orci.orcidutyroster.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "ORCI Duty Roster",
                description = "REST API Definition/Specification",
                contact = @Contact(
                        name = "Abdul-razak Twaha",
                        email = "Abdul-razak.Twaha@outlook.com"
                ),
                version = "1.0"
        )
)
@SecurityScheme(
        name = "jwt",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP
)
public class OpenApiConfig {
}
