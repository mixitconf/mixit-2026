package org.mixit.conference.faq.api

import org.springframework.web.servlet.function.ServerResponse

interface FaqHandlerApi {
    fun findAllQuestions(): ServerResponse
}
