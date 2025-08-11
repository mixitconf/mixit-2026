package org.mixit.conference.event.api

import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse

interface EventHandlerApi {
    fun findAll(request: ServerRequest): ServerResponse
    fun findOne(request: ServerRequest): ServerResponse
    fun findByYear(request: ServerRequest): ServerResponse
}