package org.mixit.conference.ui.security

import kotlinx.html.*
import org.mixit.conference.model.security.RegisteredUser
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.form.FormDescriptor
import org.mixit.conference.ui.form.FormField
import org.mixit.conference.ui.form.FormFieldType
import org.mixit.conference.ui.renderTemplate

fun registerForm(
    values: RegisteredUser? = null,
    dirty: Boolean = false,
    context: Context,
    valuesInRequest: Map<String, String?> = emptyMap(),
    converter: (FormDescriptor<RegisteredUser>) -> RegisteredUser = { throw IllegalStateException() }
) = FormDescriptor(
    fields = listOf(
        FormField(
            "email",
            type = FormFieldType.Email,
            fieldLabel = "Email",
            dirty = dirty,
            isReadonly = true,
            isRequired = true,
            defaultValue = values?.email ?: valuesInRequest["email"]
        ),
        FormField(
            "firstname",
            type = FormFieldType.Text,
            fieldLabel = context.i18n("login.firstname"),
            dirty = dirty,
            isRequired = true,
            defaultValue = values?.firstname ?: valuesInRequest["firstname"]
        ),
        FormField(
            "lastname",
            type = FormFieldType.Text,
            fieldLabel = context.i18n("login.lastname"),
            dirty = dirty,
            isRequired = true,
            defaultValue = values?.lastname ?: valuesInRequest["lastname"]
        ),
    ),
    converter = converter
)

fun renderRegisteringPage(context: Context, formValue: FormDescriptor<RegisteredUser>) =
    renderTemplate(context) {
        sectionComponent(context) {
            h1 { +context.i18n("login.title") }
        }
        sectionComponent(context) {
            p {
                +context.i18n("login.register")
            }
            formValue.form(this, action = "/signup") {
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