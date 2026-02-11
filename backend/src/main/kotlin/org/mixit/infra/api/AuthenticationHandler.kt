package org.mixit.infra.api

import org.mixit.WebContext
import org.mixit.conference.model.people.Email
import org.mixit.conference.model.security.CredentialResponse
import org.mixit.conference.model.security.Credentials
import org.mixit.conference.model.security.RegisteredUser
import org.mixit.conference.model.shared.Language
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.security.loginForm
import org.mixit.conference.ui.security.registerForm
import org.mixit.conference.ui.security.renderLoginPage
import org.mixit.conference.ui.security.renderLoginStartPage
import org.mixit.conference.ui.security.renderRegisteringPage
import org.mixit.domain.model.LoginErrorType
import org.mixit.infra.spi.manager.ManagerUserApi
import org.springframework.http.MediaType.TEXT_HTML
import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.ServerResponse.ok
import org.springframework.web.servlet.function.ServerResponse.seeOther
import java.net.URI

@Component
class AuthenticationHandler(
    private val webContext: WebContext,
    private val managerUserApi: ManagerUserApi
) {
    fun login(credentialForm: FormDescriptor<Email>): ServerResponse =
        redirectToLoginStart(credentialForm)

    fun loginStartSend(formValue: FormDescriptor<Email>): ServerResponse =
        if (formValue.isValid()) {
            val email = formValue.value()
            try {
                val response: CredentialResponse =
                    managerUserApi.action(
                        Credentials.LoginRequest(
                            email = email,
                            language = Language.FRENCH,
                        ),
                    )
                when (response) {
                    is CredentialResponse.TokenSent -> redirectToLogin(loginForm(Pair(email, null)))
                    is CredentialResponse.LoginError -> {
                        if (response.type == LoginErrorType.BAD_EMAIL_OR_LOGIN) {
                            redirectToRegister(
                                registerForm(
                                    valuesInRequest = mapOf("email" to email),
                                    dirty = false,
                                    context = webContext.ctx(),
                                )
                            )
                        } else {
                            redirectToLoginStart(formValue.copy(globalError = response.errorMessage()))
                        }
                    }

                    else -> redirectToLoginStart(formValue.copy(globalError = "Unexpected error, please try again"))
                }
            } catch (e: Exception) {
                redirectToLoginStart(formValue.copy(globalError = e.message))
            }
        } else {
            redirectToLoginStart(formValue)
        }

    private fun redirectToLoginStart(formValue: FormDescriptor<Email>) =
        ok().contentType(TEXT_HTML).body(
            renderLoginStartPage(
                context = webContext.ctx(),
                formValue,
            ),
        )

    fun logout(): ServerResponse {
        val email: Email? = webContext.ctx().email
        if (email != null) {
            managerUserApi.action(Credentials.LogoutRequest(email))
        }
        return seeOther(URI("/login")).cookie(managerUserApi.removeCookie()).build()
    }

    fun loginFinalize(formValue: FormDescriptor<Pair<Email, String?>>): ServerResponse =
        if (formValue.isValid()) {
            val email = formValue.value()
            try {
                val response =
                    managerUserApi.action(
                        Credentials.LoginRequestWithToken(
                            email = email.first,
                            token = email.second ?: throw kotlin.IllegalArgumentException("Token is missing")
                        ),
                    )
                when (response) {
                    is CredentialResponse.LoginSuccess -> {
                        val cookie = managerUserApi.createCookie(response.jwtToken, response.jwtExpiration)
                        seeOther(URI("/")).cookie(cookie).build()
                    }

                    is CredentialResponse.LoginError -> redirectToLogin(formValue.copy(globalError = response.errorMessage()))
                    else -> redirectToLogin(formValue.copy(globalError = "Unexpected error, please try again"))
                }
            } catch (e: Exception) {
                redirectToLogin(formValue.copy(globalError = e.message))
            }
        } else {
            redirectToLogin(formValue)
        }

    private fun redirectToRegister(formValue: FormDescriptor<RegisteredUser>) =
        ok().contentType(TEXT_HTML).body(
            renderRegisteringPage(
                context = webContext.ctx(),
                formValue,
            ),
        )

    fun signup(formValue: FormDescriptor<RegisteredUser>): ServerResponse =
        if (formValue.isValid()) {
            val user = formValue.value()
            try {
                val response =
                    managerUserApi.action(
                        Credentials.SignupRequest(
                            email = user.email,
                            firstname = user.firstname,
                            lastname = user.lastname,
                            language = Language.FRENCH,
                            subcribeNewsletter = true
                        ),
                    )
                when (response) {
                    is CredentialResponse.TokenSent -> redirectToLogin(loginForm(Pair(user.email, null)))
                    is CredentialResponse.LoginError -> {
                        redirectToRegister(formValue.copy(globalError = response.errorMessage()))
                    }

                    else -> redirectToRegister(formValue.copy(globalError = "Unexpected error, please try again"))
                }
            } catch (e: Exception) {
                redirectToRegister(formValue.copy(globalError = e.message))
            }
        } else {
            redirectToRegister(formValue)
        }

    private fun redirectToLogin(formValue: FormDescriptor<Pair<Email, String?>>) =
        ok().contentType(TEXT_HTML).body(
            renderLoginPage(
                context = webContext.ctx(),
                formValue,
            ),
        )
}
