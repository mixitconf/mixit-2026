package org.mixit.conference.people.handler

import org.mixit.WebContext
import org.mixit.conference.event.spi.EventRepository
import org.mixit.conference.people.api.OrgaHandlerApi
import org.mixit.conference.people.api.SpeakerHandlerApi
import org.mixit.conference.people.api.SponsorHandlerApi
import org.mixit.conference.people.spi.PeopleRepository
import org.mixit.conference.talk.spi.TalkRepository
import org.mixit.conference.ui.page.renderMixettePage
import org.mixit.conference.ui.page.renderSpeaker
import org.mixit.conference.ui.page.renderSpeakers
import org.mixit.conference.ui.page.renderSponsor
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.notFound
import org.springframework.web.servlet.function.ServerResponse.ok

@Component
class PeopleHandler(
    private val repository: PeopleRepository,
    private val eventRepository: EventRepository,
    private val talkRepository: TalkRepository,
    private val peopleRepository: PeopleRepository,
    private val webContext: WebContext,
) : SpeakerHandlerApi,
    SponsorHandlerApi,
    OrgaHandlerApi {
    override fun findSpeakerByYear(
        year: Int,
        contentType: MediaType,
    ): ServerResponse =
        repository.findSpeakerByYear(year).let { speakers ->
            val staff = repository.findStaffByYear(year).map { it.id }
            // A speaker can't be also staff
            val speakersWithoutStaff = speakers.filterNot { staff.contains(it.id) }
            when (contentType) {
                TEXT_HTML ->
                    ok().contentType(contentType).body(
                        renderSpeakers(
                            context = webContext.context!!,
                            event = eventRepository.findByYear(year)!!,
                            sponsors = peopleRepository.findSponsorByYear(year),
                            speakers = speakersWithoutStaff,
                        ),
                    )

                APPLICATION_JSON -> ok().contentType(contentType).body(speakersWithoutStaff)
                else -> notFound().build()
            }
        }

    override fun findSponsorByYear(
        year: Int,
        contentType: MediaType,
    ): ServerResponse =
        repository.findSponsorByYear(year).let { sponsors ->
            when (contentType) {
                TEXT_HTML ->
                    ok().contentType(contentType).body(
                        renderSponsor(webContext.context!!, eventRepository.findByYear(year)!!, sponsors),
                    )

                APPLICATION_JSON -> ok().contentType(contentType).body(sponsors)
                else -> notFound().build()
            }
        }

    override fun findSpeakerByLogin(login: String): ServerResponse =
        repository
            .findSpeaker(login)
            ?.let { speaker ->
                ok().contentType(TEXT_HTML).body(
                    renderSpeaker(
                        context = webContext.context!!,
                        speaker = speaker,
                        talks = talkRepository.findSpeakerTalks(speaker.id),
                    ),
                )
            }
            ?: notFound().build()

    override fun findOrganizationByYear(year: Int): ServerResponse =
        repository
            .findOrganizationByYear(year)
            .let { orgs ->
                ok().contentType(TEXT_HTML).body(
                    renderMixettePage(
                        context = webContext.context!!,
                        event = eventRepository.findByYear(year)!!,
                        organizations = orgs,
                        sponsors = repository.findSponsorByYear(year),
                    ),
                )
            }
}
