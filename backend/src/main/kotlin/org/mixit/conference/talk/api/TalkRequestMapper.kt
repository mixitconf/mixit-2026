package org.mixit.conference.talk.api

import org.mixit.conference.model.shared.Context
import org.mixit.conference.shared.model.Topic
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.page.talkSearchForm
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.paramOrNull

fun ServerRequest.toTalkCriteria(context: Context?): FormDescriptor<Pair<Topic?, String?>> {
    val params: Map<String, String?> = listOf("topic", "filter").associateWith { paramOrNull(it) }

    return talkSearchForm(
        context = context ?: Context.default(),
        valuesInRequest = params,
    ) { form ->
        val topic =
            form.field("topic")?.defaultValue?.let { value -> Topic.entries.firstOrNull { it.name == value } }
        val filter = form.field("filter")?.defaultValue
        topic to filter
    }
}
