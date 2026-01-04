package org.mixit.conference.people.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerResponse

interface SpeakerHandlerApi {
    fun findSpeakerByYear(
        year: Int,
        contentType: MediaType,
    ): ServerResponse

    fun findSpeakerByLogin(login: String): ServerResponse
}
