package org.mixit.conference.model.security

import kotlinx.datetime.Instant
import org.mixit.conference.model.people.Email
import org.mixit.conference.model.people.Role
import org.mixit.domain.model.LoginErrorType
import org.mixit.domain.model.UserErrorType


sealed class CredentialResponse {

    data class LoginError(
        val type: LoginErrorType,
    ) : CredentialResponse() {
        fun errorMessage(): String =
            when (type) {
                LoginErrorType.BAD_TOKEN -> "The token is invalid, please request a new one"
                LoginErrorType.TOKEN_EXPIRED -> "The token has expired, please request a new one"
                LoginErrorType.BAD_EMAIL_OR_LOGIN -> "The email is not registered"
            }
    }

    data class UserCreationError(
        val type: UserErrorType,
    ) : CredentialResponse()

    object UserCreated : CredentialResponse()

    object TokenSent : CredentialResponse()

    object Unauthorized : CredentialResponse()

    data class LoginSuccess(
        val username: String,
        val jwtToken: String,
        val jwtExpiration: Instant
    ) : CredentialResponse()

    object LogoutSuccess : CredentialResponse()


    data class RefreshedUser(
        val username: String,
        val role: Role,
        val email: Email
    ) : CredentialResponse()

    object UserUnknownOrInvalid : CredentialResponse()
}
