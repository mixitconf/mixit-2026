//package org.mixit.conference.ui.security
//
//import kotlinx.html.*
//import org.mixit.conference.model.people.Email
//import org.mixit.conference.model.shared.Context
//import org.mixit.conference.ui.component.*
//import org.mixit.conference.ui.form.FormDescriptor
//import org.mixit.conference.ui.form.FormField
//import org.mixit.conference.ui.form.FormFieldType
//import org.mixit.conference.ui.renderTemplate
//
//fun loginForm(
//    values: Pair<Email, String?>? = null,
//    valuesInRequest: Map<String, String?> = emptyMap(),
//    converter: (FormDescriptor<Pair<Email, String?>>) -> Pair<Email, String?> = { throw IllegalStateException() }
//) = FormDescriptor(
//    fields = listOf(
//        FormField(
//            "email",
//            type = FormFieldType.Email,
//            fieldLabel = "Email",
//            isReadonly = true,
//            isRequired = true,
//            defaultValue = values?.first ?: valuesInRequest["email"]
//        ),
//        FormField(
//            "token",
//            type = FormFieldType.Text,
//            fieldLabel = "Token",
//            isRequired = true,
//            defaultValue = values?.second ?: valuesInRequest["token"],
//            help = "Token received by email"
//        )
//    ),
//    converter = converter
//)
//
//fun renderLoginPage(context: Context, formValue: FormDescriptor<Pair<Email, String?>>) =
//    renderTemplate(context) {
//        sectionComponent(context) {
//            h1 { +context.i18n("login.title") }
//        }
//        sectionComponent(context) {
//            p {
//                +context.i18n("login.description")
//            }
//            formValue.form(this, action = "/login") {
//                button(classes = "btn mxt-btn-primary", type = ButtonType.submit) { +context.i18n("login.action.signin") }
//            }
//        }
//        sectionComponent(context) {
//            p {
//                +context.i18n("login.cookie")
//            }
//            p(classes = "mb-5") {
//                small {
//                    +context.i18n("login.rgpd")
//                }
//            }
//
//        }
//    }