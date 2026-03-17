package org.mixit.infra.api

import org.mixit.conference.model.feedback.Feedback
import org.mixit.conference.model.people.Email
import org.mixit.domain.spi.TalkRepository
import org.mixit.infra.api.dto.UserFeedbackCommand
import org.mixit.infra.config.WebContext
import org.mixit.infra.spi.manager.ManagerFeedbackApi
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.seeOther
import java.net.URI

@Component
class FeedbackHandler(
    private val talkRepository: TalkRepository,
    private val webContext: WebContext,
    private val managerFeedbackApi: ManagerFeedbackApi
) {

    fun saveFeedback(
        email: String,
        talkId: String,
        feedback: Feedback,
        toAdd : Boolean,
    ): ServerResponse {
        if(email != webContext.ctx().email) {
            return ServerResponse.status(403).build()
        }
        if(toAdd) {
            managerFeedbackApi.addUserFeedback(talkId, feedback)
        } else {
            managerFeedbackApi.deleteUserFeedback(talkId, feedback)
        }
        return ServerResponse.ok().build()
    }

    fun saveFeedbacks(
        email: Email,
        talkId: String,
        feedbacks: List<Feedback>,
        comment: String?
    ): ServerResponse {
            if(email != webContext.ctx().email) {
                return ServerResponse.status(403).build()
            }
            val userFeedback = UserFeedbackCommand(
                feedbacks = feedbacks,
                comment = comment
            )
            managerFeedbackApi.saveUserFeedbacks(talkId, userFeedback)
            val talk = talkRepository.findById(talkId) ?: return ServerResponse.notFound().build()
            return seeOther(URI("/${talk.event}/${talk.slug}")).build()
    }
}
