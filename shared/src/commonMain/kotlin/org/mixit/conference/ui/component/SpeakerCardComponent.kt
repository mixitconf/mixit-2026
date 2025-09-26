package org.mixit.conference.ui.component

import kotlinx.html.*
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.shared.Context

fun A.speakersComponentInDiv(context: Context, speakers: List<Speaker>) {
    div(classes = "mxt-speakers__container-talk mt-2") {
        speakers.forEach { speaker ->
            a(classes = "mxt-speakers__card") {
                href = "${context.uriBasePath}/speakers/${speaker.id}"
                +"${speaker.firstname} ${speaker.lastname}"
            }
        }
    }
}

fun DIV.speakersComponentInDiv(context: Context, speakers: List<Speaker>, isSmall: Boolean = false) {
    div(classes = "mxt-speakers__container mt-2") {
        speakers.forEach { speaker ->
            a(classes = "mxt-speakers__card") {
                href = "${context.uriBasePath}/speakers/${speaker.id}"
                if (isSmall) {
                    small { +"${speaker.firstname} ${speaker.lastname}" }
                } else {
                    +"${speaker.firstname} ${speaker.lastname}"
                }

            }
        }
    }
}