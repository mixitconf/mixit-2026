package org.mixit.conference.people.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerResponse

interface SponsorHandlerApi {
    fun findSponsorByYear(
        year: Int,
        contentType: MediaType,
    ): ServerResponse
}
