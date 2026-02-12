package org.mixit.infra.api

import org.mixit.conference.model.people.Email
import org.mixit.domain.spi.TalkRepository
import org.mixit.infra.spi.manager.ManagerFavoriteApi
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.seeOther
import java.net.URI

@Component
class FavoriteHandler(
    private val repository: ManagerFavoriteApi,
    private val talkRepository: TalkRepository,
) {

    fun toggleFavorite(email: Email, talkId: String): ServerResponse {
        repository.toggleFavorite(email, talkId)
        val talk = talkRepository.findById(talkId) ?: return ServerResponse.notFound().build()
        return seeOther(URI("/${talk.event}/${talk.slug}")).build()
    }
}