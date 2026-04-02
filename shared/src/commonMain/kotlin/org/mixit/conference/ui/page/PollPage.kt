package org.mixit.conference.ui.page

import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.b
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.p
import kotlinx.html.textArea
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.poll.AnniversaryPoll
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsorGroupComponent
import org.mixit.conference.ui.renderTemplate

fun renderPoll(
    context: Context,
    event: Event,
    sponsors: List<Sponsor>,
    poll: AnniversaryPoll?,
) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            h1 {
                +context.i18n("poll.title")
            }
            p(classes = "lead") {
                +context.i18n("poll.intro")
            }
            div(classes = "text-center") {
                img(src = "/images/jpg/anniversaire.jpg", classes = "mxt-style-image") {
                    alt = context.i18n("poll.title")
                }
            }
            p(classes = "mt-3") {
                +context.i18n("poll.description")
            }
            if (!context.isAuthenticated) {
                p(classes = "text-center") {
                    b {
                        +context.i18n("poll.connected")
                    }
                }
                p(classes = "text-center mb-5") {
                    a(classes = "btn mxt-btn-primary mb-5") {
                        href = "${context.uriBasePath}/login"
                        +context.i18n("login.action.log")
                    }
                }
            } else {
                form(action = "/anniversaire", method = FormMethod.post, classes = "mb-5") {
                    div(classes = "d-flex flex-column border-lg mxt-form__filter align-items-start gap-4") {
                        div(classes = "form-check form-check-inline mt-3") {
                            input(classes = "form-check-input") {
                                type = InputType.checkBox
                                id = "lotteryParticipation"
                                name = "lotteryParticipation"
                                this.checked = poll?.lotteryParticipation ?: false
                                this.value = "true"
                            }
                            label(classes = "form-check-label") {
                                htmlFor = "lotteryParticipation"
                                +context.i18n("poll.action")
                            }
                        }
                        div(classes = "form-group w-100") {
                            label(classes = "") {
                                htmlFor = "keynoteFeeds"
                                +context.i18n("poll.keynotes")
                            }
                            textArea(classes = "form-control", rows = "5") {
                                id = "keynoteFeeds"
                                name = "keynoteFeeds"
                                +(poll?.keynoteFeeds ?: "")
                            }
                        }
                        div(classes = "form-group w-100") {
                            label(classes = "") {
                                htmlFor = "conferenceFeeds"
                                +context.i18n("poll.topics")
                            }
                            textArea(classes = "form-control", rows = "5") {
                                id = "keynoteFeeds"
                                name = "conferenceFeeds"
                                +(poll?.conferenceFeeds ?: "")
                            }
                        }
                        div(classes="text-center w-100") {
                            button(classes = "btn mxt-btn-primary mt-3 mb-4") {
                                +context.i18n("feedback.action")
                            }
                        }
                    }
                }
            }

            sectionComponent(context) {
                sponsorGroupComponent(context, event, sponsors)
            }
        }

    }
