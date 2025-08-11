package org.mixit.conference.event.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.router

@Configuration
class EventRouterConfig {
    @Bean
    fun eventRouter(eventHandler: EventHandlerApi) = router {
        GET("/api/events", eventHandler::findAll)
        GET("/api/events/{id}", eventHandler::findOne)
        GET("/api/{year}/event", eventHandler::findByYear)
    }
}
