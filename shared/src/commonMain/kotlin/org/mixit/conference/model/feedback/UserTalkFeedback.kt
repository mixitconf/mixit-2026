package org.mixit.conference.model.feedback

import kotlinx.datetime.Instant
 class UserTalkFeedback {
     lateinit var id: String
     lateinit var talkId: String
     lateinit var creationInstant: String
     var notes: List<Feedback> = emptyList()
     var comment: String? = null
     var commentState: FeedbackCommentState? = null
 }
