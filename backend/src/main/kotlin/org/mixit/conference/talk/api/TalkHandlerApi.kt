package org.mixit.conference.talk.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.time.Year

interface TalkHandlerApi {

    fun findTalkByYear(year: Int, searchText: String?): ServerResponse

    fun findTalkBySlug(year: Int, slug: String): ServerResponse
}