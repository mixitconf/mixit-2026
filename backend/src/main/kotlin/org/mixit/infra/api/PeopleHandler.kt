package org.mixit.infra.api

import org.mixit.infra.config.WebContext
import org.mixit.conference.ui.page.renderMixettePage
import org.mixit.conference.ui.page.renderSpeaker
import org.mixit.conference.ui.page.renderSpeakers
import org.mixit.conference.ui.page.renderSponsor
import org.mixit.domain.spi.EventRepository
import org.mixit.domain.spi.PeopleRepository
import org.mixit.domain.spi.TalkRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse

@Component
class PeopleHandler(
    private val repository: PeopleRepository,
    private val eventRepository: EventRepository,
    private val talkRepository: TalkRepository,
    private val peopleRepository: PeopleRepository,
    private val webContext: WebContext,
) {
    fun findSpeakerByYear(year: Int): ServerResponse =
        repository.findSpeakerByYear(year).let { speakers ->
            val staff = repository.findStaffByYear(year).map { it.id }
            // A speaker can't be also staff
            val speakersWithoutStaff = speakers.filterNot { staff.contains(it.id) }

            ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
                renderSpeakers(
                    context = webContext.context!!,
                    event = eventRepository.findByYear(year)!!,
                    sponsors = peopleRepository.findSponsorByYear(year),
                    speakers = speakersWithoutStaff,
                ),
            )
        }

    fun findSponsorByYear(year: Int): ServerResponse =
        repository.findSponsorByYear(year).let { sponsors ->
            ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
                renderSponsor(webContext.context!!, eventRepository.findByYear(year)!!, sponsors),
            )
        }

    fun findSpeakerByLogin(login: String): ServerResponse =
        repository
            .findSpeaker(login)
            ?.let { speaker ->
                ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
                    renderSpeaker(
                        context = webContext.context!!,
                        speaker = speaker,
                        talks = talkRepository.findSpeakerTalks(speaker.id),
                    ),
                )
            }
            ?: ServerResponse.notFound().build()

    fun findOrganizationByYear(year: Int): ServerResponse =
        repository
            .findOrganizationByYear(year)
            .let { orgs ->
                ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(
                    renderMixettePage(
                        context = webContext.context!!,
                        event = eventRepository.findByYear(year)!!,
                        organizations = orgs,
                        sponsors = repository.findSponsorByYear(year),
                    ),
                )
            }
}