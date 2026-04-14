package org.mixit.infra.spi.manager

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class ManagerClientBuilder {
    @Bean
    fun restClientBuilder(): RestClient.Builder = RestClient.builder()
}
