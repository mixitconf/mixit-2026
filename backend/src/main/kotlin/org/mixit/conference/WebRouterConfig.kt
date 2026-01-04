package org.mixit.conference

import org.mixit.WebContext
import org.mixit.conference.event.api.EventHandlerApi
import org.mixit.conference.event.api.EventScreen
import org.mixit.conference.faq.api.FaqHandlerApi
import org.mixit.conference.talk.api.toTalkCriteria
import org.mixit.conference.media.handler.MediaHandler
import org.mixit.conference.model.shared.Context
import org.mixit.conference.people.api.OrgaHandlerApi
import org.mixit.conference.people.api.SpeakerHandlerApi
import org.mixit.conference.people.api.SponsorHandlerApi
import org.mixit.conference.talk.api.TalkHandlerApi
import org.mixit.conference.ui.CURRENT_MEDIA_YEAR
import org.mixit.conference.ui.CURRENT_TALK_YEAR
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.page.renderError
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

@Configuration
class WebRouterConfig {

    @Bean
    fun webRouter(
        eventHandler: EventHandlerApi,
        faqHandlerApi: FaqHandlerApi,
        orgaHandlerApi: OrgaHandlerApi,
        speakerHandlerApi: SpeakerHandlerApi,
        sponsorHandlerApi: SponsorHandlerApi,
        talkHandlerApi: TalkHandlerApi,
        mediaHandler: MediaHandler,
        webContext: WebContext
    ) = router {
        accept(MediaType.TEXT_HTML).nest {
            arrayOf("/", "/fr", "/en").forEach { path ->
                GET(path) {
                    eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.HOME)
                }
            }
            GET("/about") {
                eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.ABOUT)
            }
            GET("/accessibility") {
                eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.ACCESSIBILITY)
            }
            (GET("/code-of-conduct") or GET("/codeofconduct")) {
                eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.CODE_OF_CONDUCT)
            }
            (GET("/code-of-conduct") or GET("/codeofconduct")) {
                eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.CODE_OF_CONDUCT)
            }
            GET("/error") {
                ok().contentType(MediaType.TEXT_HTML).body(renderError(webContext.context?: Context.default()))
            }
            GET("/faq") {
                faqHandlerApi.findAllQuestions()
            }
            GET("/media") {
                mediaHandler.findMediaByYear(CURRENT_MEDIA_YEAR, it.param("search").orElse(null))
            }
            GET("/mixette") {
                orgaHandlerApi.findOrganizationByYear(CURRENT_MEDIA_YEAR)
            }
            GET("/schedule") {
                talkHandlerApi.findTalkByYear(CURRENT_TALK_YEAR, it.toTalkCriteria(webContext.context))
            }
            GET("/speakers") {
                speakerHandlerApi.findSpeakerByYear(CURRENT_TALK_YEAR, MediaType.TEXT_HTML)
            }
            GET("/speakers/{login}") {
                speakerHandlerApi.findSpeakerByLogin(it.pathVariable("login"))
            }
            GET("/sponsors") {
                sponsorHandlerApi.findSponsorByYear(CURRENT_YEAR, MediaType.TEXT_HTML)
            }
            GET("/talks") {
                talkHandlerApi.findTalkByYear(CURRENT_TALK_YEAR, it.toTalkCriteria(webContext.context))
            }
            GET("/user/{login}") {
                speakerHandlerApi.findSpeakerByLogin(it.pathVariable("login"))
            }
            GET("/venue") {
                eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.VENUE)
            }
            GET("/budget") {
                eventHandler.findByYear(CURRENT_YEAR, MediaType.TEXT_HTML, EventScreen.BUDGET)
            }

            (2012..CURRENT_YEAR).forEach { year ->
                GET("/$year") {
                    talkHandlerApi.findTalkByYear(year, it.toTalkCriteria(webContext.context))
                }
                GET("/$year/media") {
                    mediaHandler.findMediaByYear(year, it.param("search").orElse(null))
                }
                GET("/$year/medias/images") {
                    mediaHandler.findMediaByYear(year, it.param("search").orElse(null))
                }
                GET("/$year/medias/images/{album}") {
                    mediaHandler.findMediaByYearAndSection(year, it.pathVariable("album"))
                }
                GET("/$year/medias/images/{album}/{name}") {
                    mediaHandler.findPhoto(year, it.pathVariable("album"), it.pathVariable("name"))
                }
                GET("/$year/about") {
                    eventHandler.findByYear(year, MediaType.TEXT_HTML, EventScreen.ABOUT)
                }
                GET("/$year/mixette") {
                    orgaHandlerApi.findOrganizationByYear(year)
                }
                GET("/$year/sponsors") {
                    sponsorHandlerApi.findSponsorByYear(year, MediaType.TEXT_HTML)
                }
                GET("/$year/speakers") {
                    speakerHandlerApi.findSpeakerByYear(year, MediaType.TEXT_HTML)
                }
                GET("/$year/{slug}") {
                    talkHandlerApi.findTalkBySlug(year, it.pathVariable("slug"))
                }
            }
        }

    }
}