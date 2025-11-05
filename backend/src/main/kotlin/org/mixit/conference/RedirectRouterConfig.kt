package org.mixit.conference

import org.mixit.MixitProperties
import org.mixit.WebContext
import org.mixit.conference.model.shared.Language
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import java.net.URI

@Configuration
class RedirectRouterConfig(
    private val properties: MixitProperties
) {

    @Bean
    fun redirectRouter(
        webContext: WebContext
    ) = router {
        accept(TEXT_HTML).nest {
            GET("/cfp") {
                ServerResponse.permanentRedirect(properties.cfpUrl).build()
            }
            GET("/newsletter-subscribe") {
                ServerResponse.permanentRedirect(properties.newsLetterUrl).build()
            }

            GET("/docs/sponsor/leaflet") {
                val language = webContext.context?.language ?: Language.FRENCH
                if(language == Language.FRENCH) {
                    ServerResponse.permanentRedirect(properties.doc.fr.sponsor).build()
                } else {
                    ServerResponse.permanentRedirect(properties.doc.en.sponsor).build()
                }
            }
            GET("/docs/sponsor/mixit") {
                val language = webContext.context?.language ?: Language.FRENCH
                if(language == Language.FRENCH) {
                    ServerResponse.permanentRedirect(properties.doc.fr.sponsorForm).build()
                } else {
                    ServerResponse.permanentRedirect(properties.doc.en.sponsorForm).build()
                }
            }
            GET("/docs/sponsor/mixteen") {
                val language = webContext.context?.language ?: Language.FRENCH
                if(language == Language.FRENCH) {
                    ServerResponse.permanentRedirect(properties.doc.fr.sponsorMixteenForm).build()
                } else {
                    ServerResponse.permanentRedirect(properties.doc.en.sponsorMixteenForm).build()
                }
            }
            GET("/en/docs/sponsor/mixteen") {
                ServerResponse.permanentRedirect(properties.doc.en.sponsorMixteenForm).build()
            }
            GET("/en/docs/sponsor/leaflet") {
                ServerResponse.permanentRedirect(properties.doc.en.sponsor).build()
            }
            GET("/en/docs/sponsor/mixit") {
                ServerResponse.permanentRedirect(properties.doc.en.sponsorForm).build()
            }
            GET("/docs/sponsor/leaflet/en") {
                ServerResponse.permanentRedirect(properties.doc.en.sponsor).build()
            }
            GET("/docs/sponsor/leaflet/fr") {
                ServerResponse.permanentRedirect(properties.doc.fr.sponsor).build()
            }
            GET("/docs/sponsor/form/en") {
                ServerResponse.permanentRedirect(properties.doc.en.sponsorForm).build()
            }
            GET("/docs/sponsor/form/fr") {
                ServerResponse.permanentRedirect(properties.doc.fr.sponsorForm).build()
            }
            GET("/docs/sponsor/form/mixteen/en") {
                ServerResponse.permanentRedirect(properties.doc.en.sponsorMixteenForm).build()
            }
            GET("/docs/sponsor/form/mixteen/fr") {
                ServerResponse.permanentRedirect(properties.doc.fr.sponsorMixteenForm).build()
            }
            GET("/docs/speaker/leaflet/en") {
                ServerResponse.permanentRedirect(properties.doc.en.speaker).build()
            }
            GET("/docs/speaker/leaflet/fr") {
                ServerResponse.permanentRedirect(properties.doc.fr.speaker).build()
            }
            GET("/docs/presse/leaflet/en") {
                ServerResponse.permanentRedirect(properties.doc.en.press).build()
            }
            GET("/docs/presse/leaflet/fr") {
                ServerResponse.permanentRedirect(properties.doc.fr.press).build()
            }

            (GET("/member/{login}") or GET("/profile/{login}") or GET("/member/sponsor/{login}") or GET("/member/member/{login}")) {
                val login = it.pathVariable("login")
                ServerResponse.permanentRedirect(URI("${webContext.context?.uriBasePath}/user/$login")).build()
            }
        }
    }
}
