package org.mixit.conference.ui.page

import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.h3
import kotlinx.html.img
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.title
import kotlinx.html.unsafe
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.model.shared.Language
import org.mixit.conference.model.talk.Talk
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.languageComponent
import org.mixit.conference.ui.component.roomComponent
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.topicComponent
import org.mixit.conference.ui.component.videoComponent
import org.mixit.conference.ui.renderTemplate

fun renderTalk(context: Context, event: Event, sponsors: List<Sponsor>, talk: Talk, favorite: Boolean) =
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
                div(classes = "flex-fill") {
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
                }
                div {
                    if (context.email != null) {
                        form {
                            method = FormMethod.post
                            action = "/favorites/${context.email}/talks/${talk.id}/toggle"

                            button {
                                classes = setOf("mxt-btn-primary")
                                if (favorite) {
                                    attributes["aria-label"] = context.i18n("favorite.selected")
                                    img(src = "/images/svg/favorites/mxt-favorite-dark.svg") {
                                        alt = context.i18n("favorite.selected")
                                    }
                                    span(classes = "ms-2") {
                                        +context.i18n("favorite.selected")
                                    }
                                } else {
                                    attributes["aria-label"] = context.i18n("favorite.nonselected")
                                    img(src = "/images/svg/favorites/mxt-favorite-non-dark.svg") {
                                        alt = context.i18n("favorite.nonselected")
                                    }
                                    span(classes = "ms-2") {
                                        +context.i18n("favorite.nonselected")
                                    }
                                }
                            }
                        }
                    } else {
                        a(href = "${context.uriBasePath}/login", classes = "mxt-btn-primary") {
                            attributes["aria-label"] = context.i18n("favorite.connected")
                            img(src = "/images/svg/favorites/mxt-favorite-non-dark.svg") {
                                alt = context.i18n("favorite.nonselected")
                            }
                            span(classes = "ms-2") {
                                +context.i18n("favorite.nonselected")
                            }
                        }
                    }

                }
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
                                    raw(
                                        context.markdown(
                                            speaker.description[Language.FRENCH]
                                                ?: speaker.description[Language.ENGLISH].orEmpty()
                                        )
                                    )
                                }

                            }
                        }
                    }
                }
            }
            if (talk.videos.isNotEmpty()) {
                videoComponent(talk.videos.first())
            }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }