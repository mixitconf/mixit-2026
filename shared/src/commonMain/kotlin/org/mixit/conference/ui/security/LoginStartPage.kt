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
//fun loginStartForm(
//    email: Email? = null,
//    valuesInRequest: Map<String, String?> = emptyMap(),
//    converter: (FormDescriptor<Email>) -> Email = { throw IllegalStateException() }
//) = FormDescriptor(
//    fields = listOf(
//        FormField(
//            "email",
//            type = FormFieldType.Email,
//            fieldLabel = "Email",
//            isRequired = true,
//            defaultValue = email ?: valuesInRequest["email"]
//        )
//    ),
//    converter = converter
//)
//
//fun renderLoginStartPage(context: Context, formValue: FormDescriptor<Email>) =
//    renderTemplate(context) {
//        sectionComponent(context) {
//            h1 { +"Login" }
//
//
//        }
//        sectionComponent(context, effect = SectionEffect.BOTH, style = SectionStyle.LIGHT) {
//            p {
//                +"Fill your email and click on the Sign in button to receive a connection token. If you already have a token use the second button. If it's your first connexion, you will have to fill your firstname and lastname. If you try your chance on our ticket lottery, use the same email adress to see your lottery status."
//            }
//            formValue.form(this, action = "/login") {
//                button(classes = "btn btn-primary", type = ButtonType.submit) { +"Send me a token" }
//            }
//        }
//        sectionComponent(context) {
//            p {
//                +"This site uses cookies but only to store your identity locally. By connecting, you accept this mode of operation."
//            }
//            small {
//                +"Your data (email, lastname and firstname) are only used to allow you to connect to our website, register for our lottery, or generate your badge. We don't communicate them to anyone and the email addresses are encrypted. At any time you can ask us to partially or totally delete your data."
//            }
//        }
//    }