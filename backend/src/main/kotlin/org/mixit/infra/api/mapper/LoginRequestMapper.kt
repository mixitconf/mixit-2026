package org.mixit.infra.api.mapper

import org.mixit.conference.model.people.Email
import org.mixit.conference.model.security.RegisteredUser
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.security.loginForm
import org.mixit.conference.ui.security.loginStartForm
import org.mixit.conference.ui.security.registerForm
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.paramOrNull
import kotlin.collections.associateWith
import kotlin.collections.set

fun ServerRequest.toLoginStartForm(): FormDescriptor<Email> {
    val params: Map<String, String?> =
        listOf("email")
            .associateWith { paramOrNull(it) }

    return loginStartForm(valuesInRequest = params) { form ->
        form.field("email")?.defaultValue ?: invalidString()
    }
}

fun ServerRequest.toLoginForm(values: Pair<Email, String?>? = null, dirty: Boolean = true): FormDescriptor<Pair<Email, String?>> {
    val params: MutableMap<String, String?> = mutableMapOf()
    params["email"] = values?.first ?: paramOrNull("email")
    params["token"] = values?.second ?: paramOrNull("token")

    return loginForm(valuesInRequest = params, dirty = dirty) { form ->
        Pair(
            form.field("email")?.defaultValue ?: invalidString(),
            form.field("token")?.defaultValue,
        )
    }
}

fun ServerRequest.toRegistringForm(dirty: Boolean = true, context: Context): FormDescriptor<RegisteredUser> {
    val params: MutableMap<String, String?> = mutableMapOf()
    params["email"] = paramOrNull("email")
    params["firstname"] = paramOrNull("firstname")
    params["lastname"] = paramOrNull("lastname")

    return registerForm(valuesInRequest = params, context = context, dirty = dirty) { form ->
        RegisteredUser(
            email = form.field("email")?.defaultValue ?: invalidString(),
            firstname = form.field("firstname")?.defaultValue ?: invalidString(),
            lastname = form.field("lastname")?.defaultValue ?: invalidString(),
        )
    }
}

fun invalidString(): String = throw IllegalStateException("String field invalid")
