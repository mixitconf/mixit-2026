package org.mixit.conference.event.domain

import org.mixit.conference.event.api.EventHandlerApi
import org.mixit.conference.event.spi.EventRepository
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok

@Component
class EventHandler(private val repository: EventRepository) : EventHandlerApi {
    override fun findAll(request: ServerRequest): ServerResponse =
        ok()
            .contentType(APPLICATION_JSON)
            .body(repository.findAll())

    override fun findOne(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        return repository.findOne(id)
            ?.let { event -> ok().contentType(APPLICATION_JSON).body(event) }
            ?: notFound().build()
    }

    override fun findByYear(request: ServerRequest): ServerResponse {
        val year = request.pathVariable("year").toIntOrNull()
        return repository.findByYear(year)
            ?.let { event -> ok().contentType(APPLICATION_JSON).body(event) }
            ?: notFound().build()
    }

}