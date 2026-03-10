package org.mixit.infra.api.router

import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.infra.api.EventHandler
import org.mixit.infra.api.PeopleHandler
import org.mixit.infra.api.TalkHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.function.router

@Configuration
class ApiRouterConfig(
    private val eventHandler: EventHandler,
    private val talkHandler: TalkHandler,
    private val userHandler: PeopleHandler,
) {

    @Bean
    fun apiRouter() = router {
        (accept(MediaType.APPLICATION_JSON) and "/api").nest {
            (2012..CURRENT_YEAR).forEach { year ->
                GET("/{year}/event") {
                    eventHandler.findOneIsJson(year)
                }
                GET("/{year}/talks") {
                    talkHandler.findByYearIsJson(year)
                }
                GET("/{year}/speakers") {
                    userHandler.findByYearIsJson(year)
                }
            }
        }
    }
}
