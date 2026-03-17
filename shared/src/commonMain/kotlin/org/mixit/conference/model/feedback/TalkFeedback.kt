package org.mixit.conference.model.feedback


class TalkFeedback {
    lateinit var talkId: String
    var comments: List<String> = emptyList()
    var state: Map<Feedback, Int> = emptyMap()
}
