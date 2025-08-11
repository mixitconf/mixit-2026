package org.mixit.storage.domain.event

import kotlinx.serialization.json.Json
import org.mixit.conference.event.model.Event
import org.mixit.conference.event.model.EventMedia
import org.mixit.conference.event.spi.EventRepository
import org.mixit.conference.shared.model.Link
import org.mixit.storage.domain.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Path


@Component
class EventFileRepository : EventRepository {

    private val _data: MutableList<Event>

    init {
        val path = Path.of(ClassPathResource("data/events.json").url.path)
        val json = Files.readString(path)
        _data = Json.decodeFromString<Array<EventDto>>(json)
            .map { dto ->
                Event(
                    id = dto.id,
                    start = dto.start,
                    end = dto.end,
                    year = dto.year,
                    media = EventMedia(
                        photoUrls = dto.photoUrls.map {
                            Link(name = it.name, url = it.url)
                        },
                        videoUrl = dto.videoUrl?.let {
                            Link(name = it.name, url = it.url)
                        },
                        schedulingFileUrl = dto.schedulingFileUrl,
                        streamingUrl = dto.streamingUrl
                    )
                )
            }
            .toMutableList()
    }

    @Cacheable(Cache.EVENT_CACHE)
    override fun findAll(): List<Event> =
        _data.toList()

    override fun findOne(id: String): Event? =
        _data.firstOrNull { it.id == id }

    @Cacheable(Cache.EVENT_CACHE_DETAIL)
    override fun findByYear(year: Int?): Event? =
        _data.firstOrNull { it.year == year }

}