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

fun renderSpeaker(context: Context, speaker: Speaker, talks: List<Talk>) =
    renderTemplate(context, null) {
        sectionComponent(context) {
            val talks = talks.sortedByDescending { it.event }
            h4 { +"Speaker ${talks.firstOrNull()?.event}" }
            h1 {
                img(classes = "mxt-speaker__img me-2") {
                    src = speaker.photoUrl ?: DEFAULT_IMG_URL
                    attributes["aria-label"] = "${speaker.firstname} ${speaker.lastname}"
                }
                +"${speaker.firstname} ${speaker.lastname}"
            }
            if (!speaker.company.isNullOrBlank()) {
                h4 { +speaker.company }
            }
            if (speaker.description.isNotEmpty()) {
                p {
                    unsafe {
                        raw(
                            context.markdown(
                                speaker.description[Language.FRENCH] ?: speaker.description[Language.ENGLISH].orEmpty()
                            )
                        )
                    }

                }
            }
            div(classes = "mxt-btn__group") {
                speaker.links.forEach { linkAsSecondaryButton(context, it) }
            }

            talks.forEach { talk ->
                sectionComponent(context) {
                    div(classes = "mxt-talks__container-line") {
                        a {
                            href = "${context.uriBasePath}/${talk.event}/${talk.slug}"
                            h3(classes = "mt-5") {
                                unsafe {
                                    raw(context.markdown(talk.title))
                                }
                            }
                            div(classes = "d-flex align-items-center") {
                                span(classes = "mxt-talk-event me-2") {
                                    +talk.event
                                }
                                languageComponent(context, talk)
                                talkFormatComponent(context, talk)
                                    roomComponent(context, talk)
                            }
                            if (talk.summary.isNotBlank()) {
                                p {
                                    unsafe {
                                        raw(context.markdown(talk.summary))
                                    }
                                }
                            } else {
                                p {
                                    unsafe {
                                        raw(context.markdown(talk.description))
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }  