package org.mixit.conference.model.shared

import org.mixit.conference.model.shared.Language.FRENCH

data class Context(
    val language: Language = FRENCH,
    val uriBasePath: String = if(language == FRENCH) "" else language.urlPrefix,
    val markdownRenderer: (String) -> String = { it },
    val translator: (String) -> String,
) {
    companion object {
        fun default() = Context(FRENCH) { it }
    }

    fun i18n(s: String) = translator(s)

    fun markdown(s: String) = markdownRenderer(s)
}