package org.mixit.conference.ui.component

import kotlinx.html.DIV
import kotlinx.html.span
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.talk.Talk

fun DIV.languageComponent(context: Context, talk: Talk) {
    span(classes = "mxt-talk-language me-2") {
        +talk.language.toLanguageTag().uppercase()
    }
}