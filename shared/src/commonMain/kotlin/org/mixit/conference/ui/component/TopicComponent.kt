package org.mixit.conference.ui.component

import kotlinx.html.A
import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h4
import kotlinx.html.img
import kotlinx.html.span
import kotlinx.html.unsafe
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.shared.model.Topic

fun DIV.topicComponent(context: Context, topic: Topic, year: Int? = null) {
    div(classes="mxt-topic__container") {
        val t = topic.value.lowercase()
        img {
            src = "/images/svg/topic/mxt-icon--$t.svg"
            alt = context.i18n("topics.$t.title")
        }
        div {
            +context.i18n("topics.$t.title")
            if(year != null) {
                +" $year"
            }
        }
    }
}

fun A.topicPrefixComponent(context: Context, topic: Topic, rawText: String) {
    h4(classes="mxt-talk__title-container") {
        val t = topic.value.lowercase()
        div(classes="mxt-has-tooltip") {
            img {
                src = "/images/svg/topic/mxt-icon--$t.svg"
                alt = context.i18n("topics.$t.title")
            }
            span(classes = "mxt-tooltip small") { +context.i18n("topics.$t.title") }
        }
        div(classes = "mxt-talk__title-container-wrapper") {
            unsafe {
                raw(context.markdown(rawText))
            }
        }
    }
}
