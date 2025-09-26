package org.mixit.conference.people.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.time.Year

interface OrgaHandlerApi {
    fun findOrganizationByYear(year: Int): ServerResponse
}