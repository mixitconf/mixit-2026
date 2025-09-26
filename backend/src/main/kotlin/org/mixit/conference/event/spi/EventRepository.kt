package org.mixit.conference.event.spi

import org.mixit.conference.model.event.Event

interface EventRepository {
    fun findAll(): List<Event>
    fun findOne(id: String): Event?
    fun findByYear(year: Int?): Event?
}