package org.mixit.conference.model.shared

import org.mixit.conference.model.people.Role
import org.mixit.conference.model.shared.Language.FRENCH

data class Context(
    val language: Language = FRENCH,
    val uriBasePath: String = if(language == FRENCH) "" else language.urlPrefix,
    val markdownRenderer: (String) -> String = { it },
    val isAuthenticated: Boolean = false,
    val email: String? = null,
    val role: Role = Role.USER,
    val path: String = "",
    val translator: (String) -> String,
) {
    companion object {
        fun default() = Context(FRENCH) { it }
    }

    fun i18n(s: String) = translator(s)

    fun markdown(s: String) = markdownRenderer(s)

    fun forceUriBAsePath() = if(language == Language.FRENCH) "/fr" else uriBasePath
}