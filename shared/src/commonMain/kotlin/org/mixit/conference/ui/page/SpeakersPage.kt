package org.mixit.conference.ui.page

import kotlinx.html.*
import org.mixit.conference.model.event.Event
import org.mixit.conference.model.people.Speaker
import org.mixit.conference.model.people.Sponsor
import org.mixit.conference.model.shared.Context
import org.mixit.conference.ui.DEFAULT_IMG_URL
import org.mixit.conference.ui.component.sectionComponent
import org.mixit.conference.ui.component.sponsor.sponsorGroupComponent
import org.mixit.conference.ui.component.yearSelectorComponent
import org.mixit.conference.ui.renderTemplate

fun renderSpeakers(
    context: Context,
    event: Event,
    speakers: List<Speaker>,
    sponsors: List<Sponsor>,
) =
    renderTemplate(context, event) {
        sectionComponent(context) {
            val pageTitle = context.i18n("speaker.title")
            h1 {
                img(classes = "mxt-img__header-speaker") {
                    alt = pageTitle
                }
                +pageTitle
            }
            yearSelectorComponent(context, event, url = "/speakers", alt = pageTitle)

            p(classes = "lead mt-3") {
                +context.i18n("speaker.intro")
            }
            p {
                +context.i18n("speaker.selection")
            }

            div(classes = "mxt-speaker__grid") {
                speakers.sortedBy { it.firstname }.forEach {
                    a(classes = "mxt-speaker") {
                        href = "${context.uriBasePath}/speakers/${it.id}"
                        attributes["alt"] = "${it.firstname} ${it.lastname}"
                        attributes["aria-label"] = "${it.firstname} ${it.lastname}"

                        h4 { +"${it.firstname}" }
                        div { +"${it.lastname}" }
                        img(classes = "mxt-speaker__img") {
                            src = it.photoUrl ?: DEFAULT_IMG_URL
                            attributes["aria-label"] = "${it.firstname} ${it.lastname} photo"
                        }
                    }
                }
            }
        }
        sectionComponent(context) {
            sponsorGroupComponent(context, event, sponsors)
        }
    }