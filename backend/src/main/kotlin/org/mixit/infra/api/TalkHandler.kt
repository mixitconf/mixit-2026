package org.mixit.infra.api

import org.mixit.WebContext
import org.mixit.conference.model.talk.TalkFormat
import org.mixit.conference.shared.model.Topic
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.page.renderTalk
import org.mixit.conference.ui.page.renderTalks
import org.mixit.domain.spi.EventRepository
import org.mixit.domain.spi.PeopleRepository
import org.mixit.domain.spi.TalkRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse

@Component
class TalkHandler(
    private val talkRepository: TalkRepository,
    private val peopleRepository: PeopleRepository,
    private val eventRepository: EventRepository,
    private val webContext: WebContext,
) {
    fun findTalkByYear(
        year: Int,
        filter: FormDescriptor<Pair<Topic?, String?>>,
    ): ServerResponse {
        val (topic, searchText) = filter.value()
        val event = eventRepository.findByYear(year) ?: return ServerResponse.notFound().build()
        val talks =
            talkRepository
                .findByYear(year)
                .filter { it.search(searchText) && it.format != TalkFormat.ON_AIR && it.searchByTopic(topic) }
                .groupBy { it.startLocalTime() }

        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderTalks(
                webContext.context!!,
                event,
                peopleRepository.findSponsorByYear(year),
                talks,
                filter,
            ),
        )
    }

    fun findTalkBySlug(
        year: Int,
        slug: String,
    ): ServerResponse {
        val event = eventRepository.findByYear(year) ?: return ServerResponse.notFound().build()
        val talk =
            talkRepository
                .findBySlug(year, slug)
                ?: return ServerResponse.notFound().build()

        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderTalk(
                webContext.context!!,
                event,
                peopleRepository.findSponsorByYear(year),
                talk,
            ),
        )
    }
}