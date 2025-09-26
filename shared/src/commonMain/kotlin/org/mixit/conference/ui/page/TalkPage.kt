package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.*
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.formatter.formatTime
import org.mixit.conference.ui.renderTemplate

fun renderTalk(context: Context, event: Event, sponsors: List<Sponsor>, talk: Talk) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = talk.title
            h2 {
                img(classes = "mxt-img__header-talk") {
                    alt = pageTitle
                }
                +context.i18n("talk.format.${talk.format.name}")
            }
            h1 {
                unsafe {
                    raw(context.markdown(talk.title))
                }
            }
            div(classes = "d-flex align-items-center mb-4") {
                languageComponent(context, talk)
                if (talk.videos.isNotEmpty()) {
                    img(classes = "mxt-img__header-video ms-2") {
                        attributes["aria-label"] = context.i18n("talks.video.available")
                        title = context.i18n("talks.video.available")
                    }
                }
                roomComponent(context, talk, withCapacity = true, withLink = true, withTime = true)

            }
            topicComponent(context, talk.topic)
            div(classes = "mt-3") {
                unsafe {
                    raw(context.markdown(talk.summary))
                }
                unsafe {
                    raw(context.markdown(talk.description))
                }
            }

            talk.speakers.forEach { speaker ->
                sectionComponent(context) {
                    div(classes = "d-flex align-items-center mb-4") {
                        img(classes = "mxt-speaker__img me-2") {
                            src = speaker.photoUrl ?: DEFAULT_IMG_URL
                            attributes["aria-label"] = "${speaker.firstname} ${speaker.lastname}"
                        }
                        div {
                            h3 {
                                +"${speaker.firstname} ${speaker.lastname}"
                            }
                            p {
                                unsafe {
                                    raw(context.markdown(speaker.description[Language.FRENCH]?:speaker.description[Language.ENGLISH].orEmpty()))
                                }

                            }
                        }
                    }
                }
            }
            if(talk.videos.isNotEmpty()) {
                videoComponent(talk.videos.first())
            }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }