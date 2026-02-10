package org.mixit.infra.api.mapper

import org.mixit.conference.model.people.Email
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.security.loginForm
import org.mixit.conference.ui.security.loginStartForm
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

fun invalidString(): String = throw IllegalStateException("String field invalid")
