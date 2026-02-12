package org.mixit.infra.spi.manager

import org.mixit.conference.model.favorite.Favorite
import org.mixit.conference.model.shared.Context
import org.mixit.infra.api.WebFilter
import org.mixit.infra.config.WebContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ManagerFavoriteApi(
    private val restClient: RestClient,
    private val context: WebContext
) {
    fun toggleFavorite(email: String, talkId: String): String? =
        restClient
            .post()
            .uri("/favorites/$email/talks/$talkId/toggle")
            .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body<String>()


    fun getFavorites(email: String): List<Favorite> =
        restClient
            .get()
            .uri("/favorites/$email")
            .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body<Array<Favorite>>()
            ?.toList()
            ?: emptyList()

    fun getFavorite(email: String, talkId: String): Boolean =
        restClient
            .get()
            .uri("/favorites/$email/talks/$talkId")
            .cookie(WebFilter.AUTHENT_COOKIE, context.requiredToken())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body<String>()
            ?.let {
                if(it.contains("true", ignoreCase = true)) true else false
            }
            ?: false
}