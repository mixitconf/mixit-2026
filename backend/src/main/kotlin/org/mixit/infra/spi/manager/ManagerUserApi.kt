package org.mixit.infra.spi.manager

import org.mixit.infra.config.MixitProperties
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import tools.jackson.databind.ObjectMapper

@Service
class ManagerUserApi(
    private val objectMapper: ObjectMapper,
    private val restClientBuilder: RestClient.Builder,
    private val properties: MixitProperties
) {
    companion object {
        const val ACTION_REFRESH = "/api/user/refresh"
        const val ACTION_LOGIN = "/api/login"
        const val ACTION_REGISTER = "/api/register"
        const val ACTION_LOGOUT = "/api/logout"
        const val ACTION_LOGIN_FINALIZE = "/api/login-finalize"
    }
    private val restClient: RestClient by lazy {
        restClientBuilder
            .baseUrl(properties.managerUrl)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build()
    }

    fun checkUserAndRole(token: String): AuthenticatedUserDto? {
        return restClient.post()
            .uri(ACTION_REFRESH)
            .body(token)
            .retrieve()
            .body(AuthenticatedUserDto::class.java)
    }

    fun logout(email: String) {
        restClient.post()
            .uri(ACTION_LOGOUT)
            .body(mapOf("email" to email))
            .retrieve()
    }

    fun login(email: String? = null): AuthenticatedUserDto? {
        val body = email?.let { mapOf("email" to it) } ?: emptyMap<String, String>()
        return restClient.post()
            .uri(ACTION_LOGIN)
            .body(body)
            .retrieve()
            .body(AuthenticatedUserDto::class.java)
    }

    fun loginFinalize(email: String, token: String): AuthenticatedUserDto? {
        val body = mapOf("email" to email, "token" to token)
        return restClient.post()
            .uri(ACTION_LOGIN_FINALIZE)
            .body(body)
            .retrieve()
            .body(AuthenticatedUserDto::class.java)
    }

    fun register(email: String, firstname: String, lastname: String, language: String, subscribeNewsletter: Boolean): AuthenticatedUserDto? {
        val body = mapOf(
            "email" to email,
            "firstname" to firstname,
            "lastname" to lastname,
            "language" to language,
            "subscribeNewsletter" to subscribeNewsletter
        )
        return restClient.post()
            .uri(ACTION_REGISTER)
            .body(body)
            .retrieve()
            .body(AuthenticatedUserDto::class.java)
    }
}