package org.mixit.infra.api.dto

data class AnniversaryPollCommand(
    val lotteryParticipation: Boolean,
    val keynoteFeeds: String,
    val conferenceFeeds: String,
)
