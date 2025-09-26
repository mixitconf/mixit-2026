package org.mixit.conference.event.api

import org.springframework.http.MediaType
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import java.time.Year

interface FaqHandlerApi {
    fun findAllQuestions(): ServerResponse
}