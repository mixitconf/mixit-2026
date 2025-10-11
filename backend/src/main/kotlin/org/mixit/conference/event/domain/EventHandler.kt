package org.mixit.conference.event.domain

import org.mixit.WebContext
import org.mixit.conference.event.api.EventHandlerApi
import org.mixit.conference.event.api.EventScreen
import org.mixit.conference.event.spi.EventRepository
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.shared.Context
import org.mixit.conference.people.spi.PeopleRepository
import org.mixit.conference.talk.spi.TalkRepository
import org.mixit.conference.ui.home.renderHomePage
import org.mixit.conference.ui.page.renderAboutPage
import org.mixit.conference.ui.page.renderAccessibilityPage
import org.mixit.conference.ui.page.renderBudgetPage
import org.mixit.conference.ui.page.renderCodeOfConduct
import org.mixit.conference.ui.page.renderMixettePage
import org.mixit.conference.ui.page.renderVenuePage
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok

@Component
class EventHandler(
    private val repository: EventRepository,
    private val peopleRepository: PeopleRepository,
    private val talkRepository: TalkRepository,
    private val webContext: WebContext
) : EventHandlerApi {

    override fun findOne(id: String, contentType: MediaType): ServerResponse =
        renderEvent(repository.findOne(id), contentType, EventScreen.HOME)

    override fun findByYear(
        year: Int?,
        contentType: MediaType,
        eventScreen: EventScreen
    ): ServerResponse =
        renderEvent(repository.findByYear(year), contentType, eventScreen)


    private fun renderEvent(event: Event?, contentType: MediaType, eventScreen: EventScreen): ServerResponse =
        event
            ?.let {
                val context = webContext.context ?: Context.default()
                when (contentType) {
                    TEXT_HTML -> {
                        val sponsors = event.year.let { peopleRepository.findSponsorByYear(it) }
                        ok().contentType(contentType).body(
                            when (eventScreen) {
                                EventScreen.CODE_OF_CONDUCT -> renderCodeOfConduct(context, event, sponsors)
                                EventScreen.VENUE -> renderVenuePage(context, event, sponsors)
                                EventScreen.BUDGET -> renderBudgetPage(context, event, sponsors)
                                EventScreen.ACCESSIBILITY -> renderAccessibilityPage(context, event, sponsors)
                                EventScreen.ABOUT -> renderAboutPage(
                                    context,
                                    event,
                                    staff = peopleRepository.findStaffByYear(event.year),
                                    volunteers = peopleRepository.findVolunteerByYear(event.year),
                                    sponsors = peopleRepository.findSponsorByYear(event.year)
                                )

                                EventScreen.HOME -> renderHomePage(
                                    context,
                                    event,
                                    sponsors,
                                    talkRepository.findRandomKeynote(3)
                                )
                            }
                        )
                    }

                    APPLICATION_JSON -> ok().contentType(contentType).body(event)
                    else -> notFound().build()
                }
            }
            ?: notFound().build()
}