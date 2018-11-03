package com.happytown.application.rest;

import org.junit.jupiter.api.Test;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class SwaggerConfigTest {

    SwaggerConfig swaggerConfig = new SwaggerConfig();

    @Test
    void api_shouldReturnDocketConfigurationForSwagger2() {
        // Given

        // When
        Docket docket = swaggerConfig.api();

        // Then
        assertThat(docket.getDocumentationType()).isEqualTo(DocumentationType.SWAGGER_2);
    }

}