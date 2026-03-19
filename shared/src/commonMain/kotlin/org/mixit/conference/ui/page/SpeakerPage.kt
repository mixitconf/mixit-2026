package org.mixit.conference.ui.page

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.h3
import kotlinx.html.h4
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.unsafe
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.languageComponent
import org.mixit.conference.ui.component.linkAsSecondaryButton
import org.mixit.conference.ui.component.roomComponent
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsorGroupComponent
import org.mixit.conference.ui.component.talkFormatComponent
import org.mixit.conference.ui.renderTemplate

fun renderSpeaker(context: Context, speaker: Speaker, talks: List<Talk>, sponsors: List<Sponsor>, event: Event?) =
    renderTemplate(context, null) {
        sectionComponent(context) {
            val talks = talks.sortedByDescending { it.event }
            h4(classes = "mt-3") { +"Speaker ${talks.firstOrNull()?.event}" }
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
                    div(classes = "mxt-talks__container-line mb-4") {
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

            if (sponsors.isNotEmpty() && event != null) {
                sectionComponent(context) {
                    sponsorGroupComponent(context, event, sponsors)
                }
            }
        }
    }  
