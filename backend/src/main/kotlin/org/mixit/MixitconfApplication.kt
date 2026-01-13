package org.mixit

import org.mixit.infra.config.MixitProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(MixitProperties::class)
class MixitconfApplication

fun main(args: Array<String>) {
    runApplication<MixitconfApplication>(*args)
}
