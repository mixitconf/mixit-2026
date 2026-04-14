package org.mixit.infra.spi.manager

import org.mixit.conference.model.feedback.Feedback
import org.mixit.conference.model.feedback.TalkFeedback
import org.mixit.conference.model.feedback.UserTalkFeedback
import org.mixit.infra.api.WebFilter
import org.mixit.infra.api.dto.UserFeedbackCommand
import org.mixit.infra.config.WebContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ManagerFeedbackApi(
    private val restClient: RestClient,
    private val context: WebContext,
) {
    fun addUserFeedback(
        talkId: String,
        feedback: Feedback,
    ) = restClient
        .post()
        .uri("/feedbacks/$talkId/user/${feedback.name}")
        .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
        .retrieve()
        .body<String>()

    fun deleteUserFeedback(
        talkId: String,
        feedback: Feedback,
    ) = restClient
        .delete()
        .uri("/feedbacks/$talkId/user/${feedback.name}")
        .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
        .retrieve()
        .body<String>()

    fun saveUserFeedbacks(
        talkId: String,
        command: UserFeedbackCommand,
    ) = restClient
        .post()
        .uri("/feedbacks/user/$talkId")
        .body(command)
        .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .body<String>()

    fun getUserFeedback(talkId: String): UserTalkFeedback? =
        if (context.context?.token == null) {
            null
        } else {
            restClient
                .get()
                .uri("/feedbacks/$talkId/user")
                .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<UserTalkFeedback>()
        }

    fun getAllTalkFeedback(talkId: String): TalkFeedback? =
        if (context.context?.token == null) {
            restClient
                .get()
                .uri("/public/feedbacks/talk/$talkId")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<TalkFeedback>()
        } else {
            restClient
                .get()
                .uri("/feedbacks/talk/$talkId")
                .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<TalkFeedback>()
        }
}
