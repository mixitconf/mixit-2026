package org.mixit.domain.spi

import org.mixit.conference.model.event.Event
import org.mixit.infra.spi.event.EventDto

interface EventRepository {
    fun findAll(): List<Event>

    fun findOne(id: String): Event?

    fun findByYear(year: Int?): Event?

    fun exportOne(year: Int?): EventDto?
}
