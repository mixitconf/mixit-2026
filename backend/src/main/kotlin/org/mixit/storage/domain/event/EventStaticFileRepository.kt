package org.mixit.storage.domain.event

import org.mixit.Constants
import org.mixit.storage.domain.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path


@Component
class EventStaticFileRepository {
    private val _data: MutableList<EventDto>

    init {
        val path = Path.of(ClassPathResource("data/events.json").url.path)
        val json = Files.readString(path)
        _data = Constants.serializer.decodeFromString<Array<EventDto>>(json).toMutableList()
    }

    @Cacheable(Cache.EVENT_CACHE)
    fun findAll(): List<EventDto> =
        _data.toList()

}