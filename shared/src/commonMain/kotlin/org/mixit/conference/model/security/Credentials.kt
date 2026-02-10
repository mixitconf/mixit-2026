package org.mixit.conference.model.security

import org.mixit.conference.model.shared.Language

/**
 * Represents different types of credentials. Ea
 */
sealed class Credentials {
    data class LoginRequest(
        val email: String?,
        val language: Language,
        val application: String = "WEBSITE",
    ) : Credentials()

    data class SignupRequest(
        val email: String?,
        val firstname: String?,
        val lastname: String?,
        val language: Language,
        val subcribeNewsletter: Boolean = false,
        val application: String = "WEBSITE",
    ) : Credentials()

    data class RefreshUser(
        val token: String
    ) : Credentials()

    data class LoginRequestWithToken(
        val email: String?,
        val token: String?,
        val application: String = "WEBSITE",
    ) : Credentials()

    data class LogoutRequest(
        val email: String?,
        val application: String = "WEBSITE",
    ) : Credentials()

    fun invalid(): Boolean =
        when (this) {
            is LoginRequest -> email == null
            is LoginRequestWithToken -> email == null || token == null
            is LogoutRequest -> false
            is SignupRequest -> email == null || firstname == null || lastname == null
            is RefreshUser -> token == null
        }
}
