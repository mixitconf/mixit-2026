package org.mixit.infra.spi.event

import jakarta.annotation.PostConstruct
import org.mixit.infra.util.cache.Cache
import org.mixit.infra.spi.DataService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

@Component
class EventStaticFileRepository(
    private val dataService: DataService
) {
    @Suppress("ktlint:standard:backing-property-naming")
    private val _data: MutableList<EventDto> = mutableListOf()

    @PostConstruct
    fun init() {
        _data.addAll(
            dataService.load(
                localPath = "data/events.json",
                remotePath = "/events",
                responseType = Array<EventDto>::class.java,
            )
        )
    }


    @Cacheable(Cache.EVENT_CACHE)
    fun findAll(): List<EventDto> = _data.toList()
}
