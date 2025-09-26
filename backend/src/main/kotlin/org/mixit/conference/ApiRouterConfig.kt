package org.mixit.conference

import org.mixit.conference.event.api.EventHandlerApi
import org.mixit.conference.event.api.EventScreen
import org.mixit.conference.people.api.SpeakerHandlerApi
import org.mixit.conference.people.api.SponsorHandlerApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.servlet.function.router

@Configuration
class ApiRouterConfig {

    @Bean
    fun apiRouter(
        eventHandler: EventHandlerApi,
        speakerHandlerApi: SpeakerHandlerApi,
        sponsorHandlerApi: SponsorHandlerApi,
    ) = router {
        GET("/api/events/{id}") {
            eventHandler.findOne(it.pathVariable("id"), APPLICATION_JSON)
        }
        (GET("/api/{year}/events") or GET("/api/{year}/event") ) {
            eventHandler.findByYear(it.pathVariable("year").toInt(), APPLICATION_JSON, EventScreen.HOME)
        }
        (GET("/api/{year}/speakers")) {
            speakerHandlerApi.findSpeakerByYear(it.pathVariable("year").toInt(), APPLICATION_JSON)
        }
        (GET("/api/{year}/sponsors")) {
            sponsorHandlerApi.findSponsorByYear(it.pathVariable("year").toInt(), APPLICATION_JSON)
        }
//        (GET("/api/{year}/talks") or GET("/api/{year}/talk")) {
//            eventHandler.findByYear(it.pathVariable("year").toInt(), APPLICATION_JSON)
//        }
    }
}