package org.mixit

import org.mixit.conference.model.people.Role
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language.ENGLISH
import org.mixit.conference.model.shared.Language.FRENCH
import org.mixit.infra.util.string.MarkdownRenderer
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.web.context.WebApplicationContext
import java.util.Locale
import java.util.Locale.FRANCE

fun buildContext(
    messageSource: MessageSource,
    markdownRenderer: MarkdownRenderer,
    requestPath: String,
    userLocale: Locale,
    email: String? = null,
    role: Role = Role.USER,
): Context =
    (if (userLocale.language == "fr") FRENCH to FRANCE else ENGLISH to Locale.ENGLISH).let { (language, locale) ->
        Context(
            language = language,
            markdownRenderer = { markdown ->
                markdownRenderer.render(markdown).replace("\n", "<br>")
            },
            path = requestPath.replace("/fr/", "/").replace("/en/", "/"),
            email = email,
            role = role,
            isAuthenticated = email != null,
            translator = { key ->
                messageSource.getMessage(key, null, locale)
            },
        )
    }

open class WebContext(
    var context: Context?,
)

@Configuration
class ContextConfig {
    @Bean
    @Scope(
        value = WebApplicationContext.SCOPE_REQUEST,
        proxyMode = ScopedProxyMode.TARGET_CLASS,
    )
    fun webContext(): WebContext = WebContext(null)
}
