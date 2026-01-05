package org.mixit.config


import kotlinx.serialization.json.Json
import org.mixit.MixitProperties
import org.mixit.infra.util.serializer.Serializer
import org.springframework.boot.http.converter.autoconfigure.ServerHttpMessageConvertersCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.HttpMessageConverters
import org.springframework.http.converter.KotlinSerializationStringHttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.client.RestClient
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class ManagerRestClientConfig(
    private val properties: MixitProperties,
) {
//    @Bean
//    fun kotlinSerializationCustomizer(): ServerHttpMessageConvertersCustomizer {
//        return ServerHttpMessageConvertersCustomizer { converters ->
//            //converters.withJsonConverter(ktxMessageConverterWithMediaType())
//            converters.withKotlinSerializationJsonConverter(ktxMessageConverterWithMediaType())
//            converters.withStringConverter(ktxMessageConverterWithMediaType())
//        }
//    }
//
////    @Bean
////    fun ktxMessageConverter() : KotlinSerializationJsonHttpMessageConverter =
////        KotlinSerializationJsonHttpMessageConverter(Serializer.serializer)
//
//    @Bean
//    fun ktxMessageConverterWithMediaType() : KotlinSerializationStringHttpMessageConverter<Json> {
//        // if you want to ignore unknown keys from json string,
//        // otherwise make sure your data class has all json keys.
//        return object : KotlinSerializationStringHttpMessageConverter<Json>(
//            Serializer.serializer,
//            MediaType.TEXT_HTML,
//            MediaType.TEXT_PLAIN,
//            MediaType.APPLICATION_JSON,
//        ){}
//    }
//    @Bean
//    fun kotlinSerializationCustomizer(): ServerHttpMessageConvertersCustomizer {
//        return ServerHttpMessageConvertersCustomizer { converters ->
//            // the predicate argument can be customized with your own function to select more precisely
//            // which payloads should be selected for Kotlinx Serialization handling
//            val converter = KotlinSerializationJsonHttpMessageConverter(Serializer.serializer, { _ -> true })
//            converters.withKotlinSerializationJsonConverter(converter)
//        }
//    }
//
//    @Bean
//    fun ktxMessageConverter() : KotlinSerializationJsonHttpMessageConverter =
//        KotlinSerializationJsonHttpMessageConverter(Serializer.serializer)
//
//    @Bean
//    fun ktxMessageConverterWithMediaType() : KotlinSerializationStringHttpMessageConverter<Json> {
//        // if you want to ignore unknown keys from json string,
//        // otherwise make sure your data class has all json keys.
//        return object : KotlinSerializationStringHttpMessageConverter<Json>(
//            Serializer.serializer,
//            MediaType.TEXT_HTML,
//            MediaType.TEXT_PLAIN,
//            MediaType.APPLICATION_JSON,
//        ){}
//    }

    @Bean
    fun restClient(): RestClient =
        RestClient
            .builder()
            .baseUrl(properties.externalData.url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .configureMessageConverters {
//                it.withKotlinSerializationJsonConverter(ktxMessageConverterWithMediaType())
//                //it.withStringConverter(ktxMessageConverter())
//            }
            .build()
}