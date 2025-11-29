package org.mixit.storage.domain.event

import org.mixit.conference.model.event.Event
import org.mixit.conference.event.spi.EventRepository
import org.mixit.storage.domain.Cache
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component


@Component
class EventFileRepository(
    private val staticFileRepository: EventStaticFileRepository
) : EventRepository {

    override fun findAll(): List<Event> =
        staticFileRepository.findAll().map { it.toEvent()}

    override fun findOne(id: String): Event? =
        staticFileRepository.findAll().firstOrNull { it.id == id }?.toEvent()


    @Cacheable(Cache.EVENT_CACHE_DETAIL)
    override fun findByYear(year: Int?): Event? =
        staticFileRepository.findAll().firstOrNull { it.year == year }?.toEvent()
}