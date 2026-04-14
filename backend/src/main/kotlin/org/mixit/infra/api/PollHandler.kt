package org.mixit.infra.api

import org.mixit.conference.model.poll.AnniversaryPoll
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.page.renderPoll
import org.mixit.conference.ui.security.loginForm
import org.mixit.conference.ui.security.renderLoginPage
import org.mixit.domain.spi.EventRepository
import org.mixit.domain.spi.PeopleRepository
import org.mixit.infra.api.dto.AnniversaryPollCommand
import org.mixit.infra.config.WebContext
import org.mixit.infra.spi.manager.ManagerPollApi
import org.mixit.infra.spi.manager.ManagerUserApi
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import java.util.Optional

@Component
class PollHandler(
    private val managerPollApi: ManagerPollApi,
    private val managerUserApi: ManagerUserApi,
    private val eventRepository: EventRepository,
    private val peopleRepository: PeopleRepository,
    private val webContext: WebContext,
) {
    fun findPoll(): ServerResponse {
        val event = eventRepository.findByYear(CURRENT_YEAR) ?: return ServerResponse.notFound().build()
        val (poll: AnniversaryPoll?, status) =
            try {
                managerPollApi.getUserPoll() to 200
            } catch (_: HttpClientErrorException.Unauthorized) {
                null to 401
            } catch (_: HttpClientErrorException.NotFound) {
                null to 404
            }

        val page =
            renderPoll(
                context = webContext.context!!,
                event = event,
                sponsors = peopleRepository.findSponsorByYear(CURRENT_YEAR),
                poll = poll,
            )

        return when (status) {
            401 -> ok().contentType(TEXT_HTML).cookie(managerUserApi.removeCookie()).body(page)
            else -> ok().contentType(TEXT_HTML).body(page)
        }
    }

    fun savePoll(
        lotteryParticipation: String,
        keynoteFeeds: Optional<String>,
        conferenceFeeds: Optional<String>,
    ): ServerResponse =
        if (webContext.context?.token == null) {
            ok().contentType(TEXT_HTML).body(
                renderLoginPage(
                    context = webContext.ctx(),
                    formValue = loginForm(),
                ),
            )
        } else {
            val event = eventRepository.findByYear(CURRENT_YEAR) ?: return ServerResponse.notFound().build()
            val (poll: AnniversaryPoll?, status) =
                try {
                    managerPollApi.savePoll(
                        AnniversaryPollCommand(
                            lotteryParticipation = lotteryParticipation.toBoolean(),
                            keynoteFeeds = keynoteFeeds.orElse("")!!,
                            conferenceFeeds = conferenceFeeds.orElse("")!!,
                        ),
                    ) to 200
                } catch (_: HttpClientErrorException.Unauthorized) {
                    null to 401
                } catch (_: HttpClientErrorException.NotFound) {
                    null to 404
                }

            val page =
                renderPoll(
                    context = webContext.context!!,
                    event = event,
                    sponsors = peopleRepository.findSponsorByYear(CURRENT_YEAR),
                    poll = poll,
                )

            when (status) {
                401 -> ok().contentType(TEXT_HTML).cookie(managerUserApi.removeCookie()).body(page)
                else -> ok().contentType(TEXT_HTML).body(page)
            }
        }
}
