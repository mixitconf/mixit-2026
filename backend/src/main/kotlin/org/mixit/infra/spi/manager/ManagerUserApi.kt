package org.mixit.infra.spi.manager

import jakarta.servlet.http.Cookie
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.mixit.conference.model.security.AuthenticationResponse
import org.mixit.conference.model.security.CredentialResponse
import org.mixit.conference.model.security.Credentials
import org.mixit.domain.model.JwtToken
import org.mixit.domain.model.LoginErrorType
import org.mixit.infra.api.WebFilter
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class ManagerUserApi(
    private val restClient: RestClient
) {
    /**
     * Performs an action based on the provided [credentials]. For example the caller can
     * - login
     * - change password
     * - reinit password
     * - logout
     *
     * In the response, the caller will receive a [org.mixit.conference.model.security.CredentialResponse] object representing the result of the action.
     * It contains the [User] object if the action was successful, or an error type if the action failed.
     *
     * @param credentials The credentials used to perform the action.
     * @return A [org.mixit.conference.model.security.CredentialResponse] object representing the result of the action.
     */
    fun action(credentials: Credentials): CredentialResponse =
        if (credentials.invalid()) {
            CredentialResponse.Unauthorized
        } else {
            when (credentials) {
                is Credentials.LoginRequest -> login(credentials)
                is Credentials.LogoutRequest -> logout(credentials)
                is Credentials.LoginRequestWithToken -> loginWithToken(credentials)
                is Credentials.SignupRequest -> signup(credentials)
                is Credentials.RefreshUser -> checkUserAndRole(credentials.token)
            }
        }


    /**
     * Create a cookie string from a JWT token.
     *
     * @param jwtToken The JWT token to create the cookie from.
     * @return The cookie string.
     */
    fun createCookie(
        jwtToken: JwtToken,
        jwtExpiration: Instant,
    ): Cookie {
        val cookie = Cookie(WebFilter.Companion.AUTHENT_COOKIE, jwtToken)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = (jwtExpiration.epochSeconds - Clock.System.now().epochSeconds).toInt()
        return cookie
    }

    fun removeCookie(): Cookie {
        val cookie = Cookie(WebFilter.AUTHENT_COOKIE, "")
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = 0
        return cookie
    }

    private fun login(credentials: Credentials.LoginRequest): CredentialResponse =
        try {
            restClient
                .post()
                .uri { it.path("/public/login").queryParam("email", credentials.email!!).build() }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<CredentialResponse>()
                ?: CredentialResponse.TokenSent
        } catch (_: Exception) {
            CredentialResponse.LoginError(LoginErrorType.BAD_EMAIL_OR_LOGIN)
        }

    private fun checkUserAndRole(token: String): CredentialResponse =
        try {
            restClient.post()
                .uri("/public/check-token")
                .cookie(WebFilter.AUTHENT_COOKIE, token)
                .retrieve()
                .body(AuthenticatedUserDto::class.java)
                ?.let {
                    CredentialResponse.RefreshedUser(
                        username = it.username,
                        role = it.role,
                        email = it.email
                    )
                } ?: CredentialResponse.UserUnknownOrInvalid
        } catch (_: Exception) {
            CredentialResponse.LoginError(LoginErrorType.BAD_EMAIL_OR_LOGIN)
        }

    private fun logout(credentials: Credentials.LogoutRequest): CredentialResponse =
        try {
            restClient
                .get()
                .uri { it.path("/public/logout").queryParam("email", credentials.email!!).build() }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<CredentialResponse>()
                ?: CredentialResponse.Unauthorized
        } catch (_: Exception) {
            CredentialResponse.Unauthorized
        }

    private fun loginWithToken(credentials: Credentials.LoginRequestWithToken): CredentialResponse =
        try {
            restClient
                .post()
                .uri {
                    it.path("/public/login-finalize")
                        .queryParam("email", credentials.email!!)
                        .queryParam("token", credentials.token!!)
                        .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<AuthenticationResponse>()
                ?.let {
                    CredentialResponse.LoginSuccess(
                        username = it.username,
                        jwtToken = it.jwtToken,
                        jwtExpiration = Instant.Companion.parse(it.jwtExpiration)
                    )
                } ?: CredentialResponse.LoginError(LoginErrorType.BAD_TOKEN)
        } catch (e: Exception) {
                CredentialResponse.LoginError(LoginErrorType.BAD_TOKEN)
        }

    private fun signup(credentials: Credentials.SignupRequest): CredentialResponse =
        try {
            restClient
                .post()
                .uri {
                    it.path("/public/register")
                        .queryParam("email", credentials.email!!)
                        .queryParam("firstname", credentials.firstname!!)
                        .queryParam("lastname", credentials.lastname!!)
                        .queryParam("language", credentials.language.name)
                        .queryParam("newsletter", "off")
                        .build()
                }
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body<CredentialResponse>()
                ?: CredentialResponse.TokenSent
        } catch (_: Exception) {
            CredentialResponse.LoginError(LoginErrorType.BAD_EMAIL_OR_LOGIN)
        }
}