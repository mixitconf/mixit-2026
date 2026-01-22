package org.mixit.conference

import org.mixit.config.WebContext
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.CURRENT_MEDIA_YEAR
import org.mixit.conference.ui.CURRENT_TALK_YEAR
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.page.renderError
import org.mixit.domain.api.EventScreen
import org.mixit.infra.api.EventHandler
import org.mixit.infra.api.FaqHandler
import org.mixit.infra.api.MediaHandler
import org.mixit.infra.api.PeopleHandler
import org.mixit.infra.api.TalkHandler
import org.mixit.infra.api.mapper.toTalkCriteria
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

@Configuration
class WebRouterConfig {
    @Bean
    fun webRouter(
        eventHandler: EventHandler,
        faqHandler: FaqHandler,
        peopleHandler: PeopleHandler,
        talkHandler: TalkHandler,
        mediaHandler: MediaHandler,
        webContext: WebContext,
    ) = router {
        accept(MediaType.TEXT_HTML).nest {
            arrayOf("/", "/fr", "/en").forEach { path ->
                GET(path) {
                    eventHandler.findByYear(CURRENT_YEAR, EventScreen.HOME)
                }
            }
            GET("/about") {
                eventHandler.findByYear(CURRENT_YEAR, EventScreen.ABOUT)
            }
            GET("/accessibility") {
                eventHandler.findByYear(CURRENT_YEAR,  EventScreen.ACCESSIBILITY)
            }
            (GET("/code-of-conduct") or GET("/codeofconduct")) {
                eventHandler.findByYear(CURRENT_YEAR,  EventScreen.CODE_OF_CONDUCT)
            }
            (GET("/code-of-conduct") or GET("/codeofconduct")) {
                eventHandler.findByYear(CURRENT_YEAR, EventScreen.CODE_OF_CONDUCT)
            }
            GET("/error") {
                ok().contentType(MediaType.TEXT_HTML).body(renderError(webContext.context ?: Context.default()))
            }
            GET("/faq") {
                faqHandler.findAllQuestions()
            }
            GET("/media") {
                mediaHandler.findMediaByYear(CURRENT_MEDIA_YEAR, it.param("search").orElse(null))
            }
            GET("/mixette") {
                peopleHandler.findOrganizationByYear(CURRENT_MEDIA_YEAR)
            }
            GET("/schedule") {
                talkHandler.findTalkByYear(CURRENT_TALK_YEAR, it.toTalkCriteria(webContext.context))
            }
            GET("/speakers") {
                peopleHandler.findSpeakerByYear(CURRENT_TALK_YEAR)
            }
            GET("/speakers/{login}") {
                peopleHandler.findSpeakerByLogin(it.pathVariable("login"))
            }
            GET("/sponsors") {
                peopleHandler.findSponsorByYear(CURRENT_YEAR)
            }
            GET("/talks") {
                talkHandler.findTalkByYear(CURRENT_TALK_YEAR, it.toTalkCriteria(webContext.context))
            }
            GET("/user/{login}") {
                peopleHandler.findSpeakerByLogin(it.pathVariable("login"))
            }
            GET("/venue") {
                eventHandler.findByYear(CURRENT_YEAR, EventScreen.VENUE)
            }
            GET("/budget") {
                eventHandler.findByYear(CURRENT_YEAR, EventScreen.BUDGET)
            }

            (2012..CURRENT_YEAR).forEach { year ->
                GET("/$year") {
                    talkHandler.findTalkByYear(year, it.toTalkCriteria(webContext.context))
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
                    eventHandler.findByYear(year, EventScreen.ABOUT)
                }
                GET("/$year/mixette") {
                    peopleHandler.findOrganizationByYear(year)
                }
                GET("/$year/sponsors") {
                    peopleHandler.findSponsorByYear(year)
                }
                GET("/$year/speakers") {
                    peopleHandler.findSpeakerByYear(year)
                }
                GET("/$year/{slug}") {
                    talkHandler.findTalkBySlug(year, it.pathVariable("slug"))
                }
            }
        }
    }
}
