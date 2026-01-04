package org.mixit.conference.event.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerResponse

interface EventHandlerApi {
    fun findOne(
        id: String,
        contentType: MediaType,
    ): ServerResponse

    fun findByYear(
        year: Int?,
        contentType: MediaType,
        eventScreen: EventScreen,
    ): ServerResponse
}
