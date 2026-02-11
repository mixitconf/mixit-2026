package org.mixit.infra.spi

import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import org.mixit.infra.config.MixitProperties
import org.mixit.infra.util.serializer.Serializer
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.nio.file.Files import java.nio.file.Path


/**
 * Generic service to fetch data from external API configured in ExternalDataProperties.
 * This service can fetch any type of data (TalkDto, PersonDto, FaqDto, etc.) from the remote app.
 */
@Service
class DataService(
    private val restClient: RestClient,
    private val properties: MixitProperties,
    private val resourceLoader: ResourceLoader
)  {

    /**
     * Fetch a list of items from the external API
     * @param path The API path (e.g., "/talks", "/people")
     * @param responseType The class type to deserialize each item into
     * @return A list of deserialized objects of type T, or empty list if external data is disabled or request fails
     */
    private fun <T> loadFromExternalApi(
        path: String,
        responseType: Class<Array<T>>,
    ): List<T> =
        try {
            restClient
                .get()
                .uri("/public$path")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(responseType)
                ?.toList()
                ?: emptyList()
        } catch (e: Exception) {
            // Log the error and return empty list
            // You might want to use a logger here
            println("Error fetching list from $path: ${e.message}")
            emptyList()
        }

    /**
     * Load items from a static JSON file in the classpath
     * @param path The classpath resource path (e.g., "data/faq.json", "data/talks_2024.json")
     * @param responseType The class type to deserialize each item into
     * @return A list of deserialized objects of type T, or empty list if file not found or parsing fails
     */
    private fun <T> loadFromStaticFile(
        path: String,
        responseType: Class<Array<T>>,
    ): List<T> =
        try {
            val filePath = resourceLoader.getResource("classpath:$path").filePath
            val json = Files.readString(filePath)
            // Use the serializer for the array type
            val serializer = serializer(responseType) as KSerializer<Array<T>>
            Serializer.serializer.decodeFromString(serializer, json).toList()
        } catch (e: Exception) {
            println("Error loading data from static file $path: ${e.message}")
            emptyList()
        }

    fun <T> load(
        localPath: String,
        remotePath: String,
        responseType: Class<Array<T>>
    ): List<T> =
        if (properties.externalData.enabled) {
            loadFromExternalApi(
                path = remotePath,
                responseType = responseType,
            )
        } else {
            loadFromStaticFile(
                path = localPath,
                responseType = responseType,
            )
        }
}
