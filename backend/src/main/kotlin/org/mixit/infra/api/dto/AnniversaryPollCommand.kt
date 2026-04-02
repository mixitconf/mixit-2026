package org.mixit.infra.api.dto

import org.mixit.conference.model.feedback.Feedback

data class AnniversaryPollCommand(
    val lotteryParticipation: Boolean,
    val keynoteFeeds: String,
    val conferenceFeeds: String,
)
