package org.mixit.conference.talk.api

import org.mixit.conference.shared.model.Topic
import org.mixit.conference.ui.form.FormDescriptor
import org.springframework.web.servlet.function.ServerResponse

interface TalkHandlerApi {
    fun findTalkByYear(
        year: Int,
        filter: FormDescriptor<Pair<Topic?, String?>>,
    ): ServerResponse

    fun findTalkBySlug(
        year: Int,
        slug: String,
    ): ServerResponse
}
