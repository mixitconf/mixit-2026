package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.span
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk

fun DIV.talkFormatComponent(context: Context, talk: Talk) {
    span(classes = "mxt-talk-format") {
        +"#${ context.i18n("talk.format.${talk.format.name}") }"
    }
}