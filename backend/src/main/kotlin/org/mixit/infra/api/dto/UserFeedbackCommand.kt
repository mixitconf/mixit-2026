package org.mixit.infra.api.dto

import org.mixit.conference.model.feedback.Feedback

data class UserFeedbackCommand(
    val feedbacks: List<Feedback>,
    val comment: String?,
)
