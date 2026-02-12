package org.mixit.infra.api

import org.mixit.infra.config.WebContext
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.page.renderFaqPage
import org.mixit.domain.spi.EventRepository
import org.mixit.domain.spi.FaqRepository
import org.mixit.domain.spi.PeopleRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse

@Component
class FaqHandler(
    private val repository: FaqRepository,
    private val eventRepository: EventRepository,
    private val peopleRepository: PeopleRepository,
    private val webContext: WebContext,
) {
    fun findAllQuestions(): ServerResponse =
        ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderFaqPage(
                webContext.context ?: Context.Companion.default(),
                event = eventRepository.findByYear(CURRENT_YEAR)!!,
                sponsors = peopleRepository.findSponsorByYear(CURRENT_YEAR),
                questionSets = repository.findAll(),
            ),
        )
}