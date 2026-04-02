package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.CURRENT_YEAR
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.renderTemplate

fun renderError(context: Context, status: String?) =
    renderTemplate(context, null) {
        sectionComponent(context) {
            val state = when(status) {
                "404", "401", "403" -> status.toInt()
                else -> 500
            }

            h1 {
                +context.i18n("error.$state.title")
            }

            p(classes = "mt-3 lead") {
                +context.i18n("error.$state.description")
            }

            div(classes = "text-center") {
                a(classes = "btn mxt-btn-primary mb-5") {
                    href = if (state == 401 || state == 403) {
                        "${context.uriBasePath}/login"
                    } else {
                        "${context.uriBasePath}/"
                    }
                    +context.i18n("error.$state.action")
                }
            }

            div(classes = "text-center mb-4") {
                img(src = "/images/png/error.png", classes = "mxt-style-image") {
                    alt = context.i18n("error.500.description")
                }
            }

        }



    }

