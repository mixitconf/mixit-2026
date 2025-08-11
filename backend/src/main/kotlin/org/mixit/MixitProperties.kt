package org.mixit

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("mixit")
data class MixitProperties(
    val security: SecurityProperties
)

data class SecurityProperties(
    val initVector: String,
    val key: String
)
