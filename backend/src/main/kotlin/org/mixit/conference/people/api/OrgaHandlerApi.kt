package org.mixit.conference.people.api

import org.springframework.web.servlet.function.ServerResponse

interface OrgaHandlerApi {
    fun findOrganizationByYear(year: Int): ServerResponse
}
