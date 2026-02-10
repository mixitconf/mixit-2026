package org.mixit.infra.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient


@Configuration
class ManagerRestClientConfig(
    private val properties: MixitProperties,
) {

    @Bean
    fun restClient(): RestClient =
        RestClient
            .builder()
            .baseUrl(properties.externalData.url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
}