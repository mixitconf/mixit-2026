package org.mixit.infra.spi.manager

import org.mixit.conference.model.poll.AnniversaryPoll
import org.mixit.infra.api.WebFilter
import org.mixit.infra.api.dto.AnniversaryPollCommand
import org.mixit.infra.config.WebContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ManagerPollApi(
    private val restClient: RestClient,
    private val context: WebContext,
) {
    fun getUserPoll() =
        if (context.context?.token == null) {
            restClient
                .get()
                .uri("/polls/2026")
                .retrieve()
                .body<AnniversaryPoll>()
        } else {
            restClient
                .get()
                .uri("/polls/2026")
                .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
                .retrieve()
                .body<AnniversaryPoll>()
        }

    fun savePoll(command: AnniversaryPollCommand) =
        restClient
            .post()
            .uri("/polls/2026")
            .body(command)
            .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body<AnniversaryPoll>()

    fun deletePoll(id: String) =
        restClient
            .delete()
            .uri("/polls/2026/$id")
            .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body<String>()
}
