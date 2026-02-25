package org.mixit.infra.api

import org.mixit.conference.model.talk.TalkFormat
import org.mixit.conference.shared.model.Topic
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.page.TalksCriteria
import org.mixit.conference.ui.page.renderTalk
import org.mixit.conference.ui.page.renderTalks
import org.mixit.domain.spi.EventRepository
import org.mixit.domain.spi.PeopleRepository
import org.mixit.domain.spi.TalkRepository
import org.mixit.infra.config.WebContext
import org.mixit.infra.spi.manager.ManagerFavoriteApi
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse

@Component
class TalkHandler(
    private val talkRepository: TalkRepository,
    private val peopleRepository: PeopleRepository,
    private val favoriteApi: ManagerFavoriteApi,
    private val eventRepository: EventRepository,
    private val webContext: WebContext,
) {
    fun findTalkByYear(
        year: Int,
        filter: FormDescriptor<TalksCriteria>,
    ): ServerResponse {
        val filterValues = filter.value()
        val event = eventRepository.findByYear(year) ?: return ServerResponse.notFound().build()
        val favorites = webContext.context?.email?.let { favoriteApi.getFavorites(it).map { it.talkId } } ?: emptyList()

        val talks =
            talkRepository
                .findByYear(year)
                .filter {
                    it.search(filterValues.filter)
                            && it.format != TalkFormat.ON_AIR
                            && it.searchByTopic(filterValues.topic)
                            && it.searchByFavorite(favorites, filterValues.favorites)
                }
                .sortedBy { it.startLocalTime() }
                .groupBy { it.startLocalTime() }



        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderTalks(
                webContext.context!!,
                event,
                peopleRepository.findSponsorByYear(year),
                favorites,
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

        val favorite = if (webContext.context?.email == null) false else
            favoriteApi.getFavorite(webContext.context!!.email!!, talk.id)

        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
            renderTalk(
                webContext.context!!,
                event,
                peopleRepository.findSponsorByYear(year),
                talk,
                favorite
            ),
        )
    }
}