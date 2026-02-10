package org.mixit.conference.ui.security

import kotlinx.html.*
import org.mixit.conference.model.people.Email
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.form.FormField
import org.mixit.conference.ui.form.FormFieldType
import org.mixit.conference.ui.renderTemplate

fun loginStartForm(
    email: Email? = null,
    dirty: Boolean = true,
    valuesInRequest: Map<String, String?> = emptyMap(),
    converter: (FormDescriptor<Email>) -> Email = { throw IllegalStateException() }
) = FormDescriptor(
    fields = listOf(
        FormField(
            "email",
            type = FormFieldType.Email,
            fieldLabel = "Email",
            dirty = dirty,
            isRequired = true,
            defaultValue = email ?: valuesInRequest["email"]
        )
    ),
    converter = converter
)

fun renderLoginStartPage(context: Context, formValue: FormDescriptor<Email>) =
    renderTemplate(context) {
        sectionComponent(context) {
            h1 { +context.i18n("login.title") }
        }
        sectionComponent(context) {
            p {
                +context.i18n("login.email")
            }
            formValue.form(this, action = "/login") {
                button(classes = "btn mxt-btn-primary", type = ButtonType.submit) { +context.i18n("login.action.token") }
            }
        }
        div(classes="mb-3") {
            sectionComponent(context) {
                p {
                    +context.i18n("login.cookies")
                }
                small {
                    +context.i18n("login.data")
                }
            }
        }
    }