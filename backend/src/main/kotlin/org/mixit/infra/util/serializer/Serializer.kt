package org.mixit.infra.util.serializer

import kotlinx.datetime.LocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

object Serializer {
    @OptIn(ExperimentalSerializationApi::class)
    val serializer =
        Json {
            serializersModule = SerializersModule {
                contextual(LocalDate::class, LocalDateSerializer)
            }
            encodeDefaults = true
            explicitNulls = false
            ignoreUnknownKeys = true
            allowStructuredMapKeys = true
            coerceInputValues = true
        }
}