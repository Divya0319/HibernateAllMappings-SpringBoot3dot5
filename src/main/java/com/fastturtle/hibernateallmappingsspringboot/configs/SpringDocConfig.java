package com.fastturtle.hibernateallmappingsspringboot.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Hibernate Mappings APIs",
                version = "1.0",
                description = "A Spring boot project migrated from 2.5 version to 3.5.0 to better support JPA latest standards"
        )
)
public class SpringDocConfig {
}
