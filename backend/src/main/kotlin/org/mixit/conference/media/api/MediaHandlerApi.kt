package org.mixit.conference.media.api

import org.springframework.web.servlet.function.ServerResponse

interface MediaHandlerApi {
    fun findMediaByYear(year: Int, searchText: String?): ServerResponse

    fun findMediaByYearAndSection(year: Int, sectionId: String): ServerResponse

    fun findPhoto(year: Int, sectionId: String, name: String): ServerResponse
}