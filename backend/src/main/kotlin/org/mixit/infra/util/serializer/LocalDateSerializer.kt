package org.mixit.infra.util.serializer

import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalSerializationApi::class)
object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("java.time.LocalDateAsString", PrimitiveKind.STRING)

    override fun serialize(
        encoder: Encoder,
        value: LocalDate,
    ) {
        encoder.encodeString(value.toJavaLocalDate().format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDate =
        java.time.LocalDate.parse(decoder.decodeString(), formatter).toKotlinLocalDate()
}

fun String.toLocalDate(): LocalDate =
    java.time.LocalDate.parse(this, DateTimeFormatter.ISO_LOCAL_DATE).toKotlinLocalDate()