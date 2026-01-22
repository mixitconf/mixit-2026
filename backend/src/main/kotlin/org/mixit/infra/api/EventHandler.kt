package org.mixit.infra.api

import org.mixit.MixitProperties
import org.mixit.config.WebContext
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.page.renderAboutPage
import org.mixit.conference.ui.page.renderAccessibilityPage
import org.mixit.conference.ui.page.renderBudgetPage
import org.mixit.conference.ui.page.renderCodeOfConduct
import org.mixit.conference.ui.page.renderHomePage
import org.mixit.conference.ui.page.renderVenuePage
import org.mixit.domain.api.EventScreen
import org.mixit.domain.spi.EventRepository
import org.mixit.domain.spi.PeopleRepository
import org.mixit.domain.spi.TalkRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse

@Component
class EventHandler(
    private val repository: EventRepository,
    private val peopleRepository: PeopleRepository,
    private val talkRepository: TalkRepository,
    private val webContext: WebContext,
    private val properties: MixitProperties,
) {
    fun findOne(id: String): ServerResponse =
        renderEvent(repository.findOne(id), EventScreen.HOME)

    fun findByYear(
        year: Int?,
        eventScreen: EventScreen,
    ): ServerResponse = renderEvent(repository.findByYear(year), eventScreen)

    private fun renderEvent(
        event: Event?,
        eventScreen: EventScreen,
    ): ServerResponse =
        event
            ?.let {
                val context = webContext.context ?: Context.default()
                val sponsors = event.year.let { peopleRepository.findSponsorByYear(it) }
                ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
                    when (eventScreen) {
                        EventScreen.CODE_OF_CONDUCT -> renderCodeOfConduct(context, event, sponsors)
                        EventScreen.VENUE -> renderVenuePage(context, event, sponsors)
                        EventScreen.BUDGET -> renderBudgetPage(context, event, sponsors)
                        EventScreen.ACCESSIBILITY -> renderAccessibilityPage(context, event, sponsors)
                        EventScreen.ABOUT ->
                            renderAboutPage(
                                context,
                                event,
                                staff = peopleRepository.findStaffByYear(event.year),
                                volunteers = peopleRepository.findVolunteerByYear(event.year),
                                sponsors = peopleRepository.findSponsorByYear(event.year),
                            )

                        EventScreen.HOME ->
                            renderHomePage(
                                context,
                                lastPodCastId = properties.podcastId,
                                event,
                                sponsors,
                                talkRepository.findRandomKeynote(3),
                            )
                    },
                )
            }
            ?: ServerResponse.notFound().build()
}