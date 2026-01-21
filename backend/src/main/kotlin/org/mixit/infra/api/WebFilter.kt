package org.mixit.api

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.mixit.config.WebContext
import org.mixit.config.buildContext
import org.mixit.conference.model.shared.Language
import org.mixit.infra.spi.manager.ManagerUserApi
import org.mixit.infra.util.string.MarkdownRenderer
import org.springframework.context.MessageSource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpHeaders.CONTENT_LANGUAGE
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.LocaleResolver
import java.util.Collections
import java.util.Enumeration
import java.util.Locale

@Component
class WebFilter(
    private val localeResolver: LocaleResolver,
    private val messageSource: MessageSource,
    private val markdownRenderer: MarkdownRenderer,
    private val webContext: WebContext,
    private val managerUserApi: ManagerUserApi
) : OncePerRequestFilter() {
    companion object {
        private val BOTS =
            arrayOf("Google", "Bingbot", "Qwant", "Bingbot", "Slurp", "DuckDuckBot", "Baiduspider")
        private val WEB_RESSOURCE_EXTENSIONS =
            arrayOf(".css", ".js", ".svg", ".jpg", ".png", ".webp", ".webapp", ".pdf", ".icns", ".ico", ".html")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // An english user is redirected
        if (request.isAHomePageCallFromForeignLanguage()) {
            response.sendRedirect("/${Language.ENGLISH.toLanguageTag()}/")
            return
        }
        // For a web resource we do nothing
        if (request.isWebResource()) {
            filterChain.doFilter(request, response)
            return
        }

        val isEn = request.hasLanguagePrefix(Language.ENGLISH)
        webContext.context =
            buildContext(messageSource, markdownRenderer, request.servletPath, if (isEn) Locale.ENGLISH else Locale.FRENCH)

        val uriPath =
            request.requestURI
                .let {
                    if (isEn || request.hasLanguagePrefix(Language.ENGLISH) || request.hasLanguagePrefix(Language.FRENCH)) {
                        it.substring(
                            3,
                        )
                    } else {
                        it
                    }
                }.ifBlank { "/" }

        val req =
            CustomHttpServletRequestWrapper(request, uriPath).apply {
                addHeader(CONTENT_LANGUAGE, if (isEn) "en" else "fr")
            }

        if (Regex("^/secured/.*").matches(request.requestURI)) {
            val token = request.cookies.firstOrNull { it.name == "XSRF-TOKEN" }?.value
            if (token == null) {
                response.sendRedirect("/login")
                return
            }
            val user = managerUserApi.checkUserAndRole(token)
            if (user == null) {
                response.sendRedirect("/login")
                return
            }
            webContext.context = buildContext(
                messageSource,
                markdownRenderer,
                if (isEn) Locale.ENGLISH else Locale.FRENCH,
                email = user.email,
                role = user.role,
            )

            filterChain.doFilter(request, response)
        } else {
            filterChain.doFilter(req, response)
        }
    }

    private fun HttpServletRequest.isWebResource() =
        this.requestURI.let { path ->
            WEB_RESSOURCE_EXTENSIONS.any { path.endsWith(it) }
        }

    private fun HttpServletRequest.isSearchEngineCrawler() =
        this.getHeader(HttpHeaders.USER_AGENT).let { userAgent ->
            userAgent != null && BOTS.any { userAgent.contains(it) }
        }

    private fun HttpServletRequest.isAHomePageCallFromForeignLanguage(): Boolean =
        requestURI == "/" &&
                localeResolver.resolveLocale(this) != Locale.FRENCH &&
                !isSearchEngineCrawler()

    private fun HttpServletRequest.hasLanguagePrefix(language: Language) =
        this.requestURI.startsWith(language.urlPrefix)
}

class CustomHttpServletRequestWrapper(
    request: HttpServletRequest,
    val newPath: String,
) : HttpServletRequestWrapper(request) {
    private val headers: MutableMap<String, String> by lazy {
        request.headerNames
            .asSequence()
            .map { it to request.getHeader(it) }
            .toMap()
            .toMutableMap()
    }

    fun addHeader(
        name: String,
        value: String,
    ) {
        headers[name] = value
    }

    override fun getHeader(name: String): String? = headers[name]

    override fun getHeaders(name: String): Enumeration<String?> = super.getHeaders(name)

    override fun getHeaderNames(): Enumeration<String?> =
        this.headers.keys
            .toList()
            .let { Collections.enumeration(it) }

    override fun getRequestURI(): String = newPath
}
