package org.mixit.infra.api

import org.mixit.conference.model.people.Email
import org.mixit.conference.model.security.Credentials
import org.mixit.domain.spi.TalkRepository
import org.mixit.infra.config.WebContext
import org.mixit.infra.spi.manager.ManagerFavoriteApi
import org.mixit.infra.spi.manager.ManagerUserApi
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.seeOther
import java.net.URI

@Component
class FavoriteHandler(
    private val repository: ManagerFavoriteApi,
    private val talkRepository: TalkRepository,
    private val webContext: WebContext,
    private val managerUserApi: ManagerUserApi
) {

    fun toggleFavorite(email: Email, talkId: String): ServerResponse {
        try {
            repository.toggleFavorite(email, talkId)
            val talk = talkRepository.findById(talkId) ?: return ServerResponse.notFound().build()
            return seeOther(URI("/${talk.event}/${talk.slug}")).build()
        } catch (e: Exception) {
            if (e is HttpClientErrorException.Unauthorized) {
                val email: Email? = webContext.ctx().email
                if (email != null) {
                    managerUserApi.action(Credentials.LogoutRequest(email))
                }
                return seeOther(URI("/login")).cookie(managerUserApi.removeCookie()).build()
            }
            throw e
        }

    }
}
